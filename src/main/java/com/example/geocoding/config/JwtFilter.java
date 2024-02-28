package com.example.geocoding.config;

import com.example.geocoding.exception.BadRequestException;
import com.example.geocoding.model.Company;
import com.example.geocoding.service.Services;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final Services services;

    public JwtFilter(JwtService jwtService, Services services) {
        this.jwtService = jwtService;
        this.services = services;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if (request.getServletPath().equalsIgnoreCase("/findStores")) {
            String subscriptionId = services.isSubscribed(request.getHeader("ApiKey"));
            if (subscriptionId != null) {
                filterChain.doFilter(requestWrapper, responseWrapper);
                services.saveRequestResponse(requestWrapper, request, responseWrapper, response, subscriptionId);
                responseWrapper.copyBodyToResponse();
                return;
            } else
                throw new BadRequestException("subscription not valid");
        }
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<Company> company = services.findCompany(username);
            if (company.isEmpty()) throw new UsernameNotFoundException("username not found");
            if (jwtService.isTokenValid(jwt, company.get())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(company, null, new ArrayList<>());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else throw new BadRequestException("token invalid");
        }
        filterChain.doFilter(request, response);
    }
}
