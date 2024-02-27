//package com.example.geocoding.Config;
//
//import com.example.geocoding.Repository.Service.CompanyRepoService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class ApplicationConfiguration {
//    public final CompanyRepoService companyRepoService;
//
//    public ApplicationConfiguration(CompanyRepoService companyRepoService) {
//        this.companyRepoService = companyRepoService;
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> companyRepoService.findByCompanyName(username).get();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
////        authProvider.setPasswordEncoder(new P);
//        authProvider.setPasswordEncoder(passwordEnoder());
//        return authProvider;
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEnoder() {
//        return  new BCryptPasswordEncoder();
//    }
//}
