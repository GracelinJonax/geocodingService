package com.example.geocoding.service.impl;

import com.example.geocoding.config.JwtService;
import com.example.geocoding.dto.*;
import com.example.geocoding.exception.BadRequestException;
import com.example.geocoding.model.*;
import com.example.geocoding.repository.*;
import com.example.geocoding.repository.service.CompanyRepoService;
import com.example.geocoding.repository.service.RequestResponseLogRepoService;
import com.example.geocoding.repository.service.StoreCompanyViewRepoService;
import com.example.geocoding.repository.service.SubscriptionViewRepoService;
import com.example.geocoding.service.Services;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class ServiceImpl implements Services {
    @Value("${privateKey}")
    String privatekey;
    @Value("${noCompany}")
    String companyMessage;
    @Value("${subscription}")
    String subscribe;
    private final StoreRepository storeRepository;
    private final CompanyRepository companyRepository;
    private final CompanyRepoService companyRepoService;
    private final StoreCompanyViewRepoService storeCompanyViewRepoService;
    private final PlanRepository planRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionViewRepository subscriptionViewRepository;
    private final SubscriptionViewRepoService subscriptionViewRepoService;
    private final RequestResponseLogRepoService requestResponseLogRepoService;
    private final RequestResponseLogRepository requestResponseLogRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    @Autowired
    RedisRequestCounter redisRequestCounter;


    public ServiceImpl(StoreRepository storeRepository, CompanyRepository companyRepository, CompanyRepoService companyRepoService, StoreCompanyViewRepoService storeCompanyViewRepoService, PlanRepository planRepository, SubscriptionRepository subscriptionRepository, SubscriptionViewRepository subscriptionViewRepository, SubscriptionViewRepoService subscriptionViewRepoService, RequestResponseLogRepoService requestResponseLogRepoService, RequestResponseLogRepository requestResponseLogRepository, JwtService jwtService, ModelMapper modelMapper) {
        this.storeRepository = storeRepository;
        this.companyRepository = companyRepository;
        this.companyRepoService = companyRepoService;
        this.storeCompanyViewRepoService = storeCompanyViewRepoService;
        this.planRepository = planRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionViewRepository = subscriptionViewRepository;
        this.subscriptionViewRepoService = subscriptionViewRepoService;
        this.requestResponseLogRepoService = requestResponseLogRepoService;
        this.requestResponseLogRepository = requestResponseLogRepository;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Store> saveStoreService(SaveStoreDto storeDto) {
        Optional<Company> company = companyRepository.findById(storeDto.getCompanyId());
        if (company.isEmpty()) throw new BadRequestException(companyMessage);
        Store store = new Store();
        modelMapper.map(storeDto, store);
        modelMapper.map(storeDto.getAddress(), store);

        storeRepository.save(store);
        return storeRepository.findAll();
    }

    @Override
    public List<Company> saveCompanyService(SaveCompanyDto companyDto) {
        Company company = new Company();
        modelMapper.map(companyDto.getAddress(), company);
        modelMapper.map(companyDto, company);

        companyRepository.save(company);
        return companyRepository.findAll();
    }


    @Override
    public List<StoreCompanyView> findNearStoreService(DistanceDto distanceDto) {
        Optional<Company> company = companyRepository.findById(distanceDto.getCompanyId());
        if (company.isEmpty()) throw new BadRequestException(companyMessage);
        return storeCompanyViewRepoService.findNearByStores(distanceDto.getLatitude(), distanceDto.getLongitude(), distanceDto.getDistance(), distanceDto.getCompanyId());
    }

    @Override
    public List<Plan> savePlanServices(SavePlanDto planDto) {
        if (!planDto.getPlanType().equalsIgnoreCase("unlimited")) {
            if (planDto.getTotalRequest() == null) throw new BadRequestException("totalRequest must not be null");
            else if (planDto.getTotalRequest() < 1)
                throw new BadRequestException("totalRequest must be greater than 0");
        }
        Plan plan = new Plan();
        modelMapper.map(planDto, plan);
        planRepository.save(plan);
        return planRepository.findAll();
    }

    @Override
    public List<SubscriptionView> saveSubscriptionService(SubscriptionDto subscriptionDto) {
        Optional<Company> company = companyRepository.findById(subscriptionDto.getCompanyId());
        if (company.isEmpty()) throw new BadRequestException(companyMessage);
        Optional<Plan> plan = planRepository.findById(subscriptionDto.getPlanId());
        if (plan.isEmpty()) throw new BadRequestException("no such plan present");
        Subscription subscription = new Subscription();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(subscriptionDto, subscription);
        subscription.setExpiryDate(LocalDate.now().plusDays(plan.get().getValidDays()));
        subscriptionRepository.save(subscription);
        return subscriptionViewRepository.findAll();
    }

    @Override
    public String loginService(LoginDto loginDto) {
        String token;
        HashMap<String, Object> claims = new HashMap<>();
        Optional<Company> company = companyRepoService.findByCompanyName(loginDto.getCompanyName());
        if (company.isEmpty()) throw new BadRequestException(companyMessage);
        if (company.get().getPassword().equals(loginDto.getPassword())) {
            SubscriptionView subscriptionView = subscriptionViewRepoService.findByCompanyIdAndExpiryDateAfter(company.get().getCompanyId(), LocalDate.now());
            if (subscriptionView == null) claims.put(subscribe, null);
            else claims.put(subscribe, subscriptionView.getSubscriptionId());
            token = jwtService.generateToken(claims, company.get());
            return token;
        } else throw new BadRequestException("password incorrect");
    }

    @Override
    public String isSubscribed(String apiKey) {
        byte[] decodeKey = Base64.getDecoder().decode(apiKey.getBytes(StandardCharsets.ISO_8859_1));

        byte[] pkcs8EncodedBytes = Base64.getDecoder().decode(privatekey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = kf.generatePrivate(keySpec);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            String[] decodedCipherKey = new String(cipher.doFinal(decodeKey)).split("_");

            String subscriptionId = decodedCipherKey[0];
            DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            Date date = formatter.parse(decodedCipherKey[1]);
            if (subscriptionId == null) return null;
            Optional<SubscriptionView> subscriptionView = subscriptionViewRepoService.findBySubscriptionIdAndExpiryDateAfter(subscriptionId, LocalDate.now());
            if (subscriptionView.isEmpty() || date.before(new Date()))
                throw new BadRequestException("subscriptionId or apikey expired");
            Long totalRequest = subscriptionView.get().getTotalRequest();
            if (requestResponseLogRepoService.findTopBySubscriptionId(subscriptionId).isEmpty() || totalRequest == null
                    || (subscriptionView.get().getPlanType().equalsIgnoreCase("limited")
                    && redisRequestCounter.getValue(subscriptionId) < totalRequest)
                    || (subscriptionView.get().getPlanType().equalsIgnoreCase("limited request per day")
                    && (redisRequestCounter.getValue(subscriptionId + LocalDate.now()) == null
                    || redisRequestCounter.getValue(subscriptionId + LocalDate.now()) < totalRequest)))
                return subscriptionId;
            return null;

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | BadPaddingException |
                 InvalidKeyException | IllegalBlockSizeException | ParseException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public void saveRequestResponse(ContentCachingRequestWrapper requestWrapper, HttpServletRequest request, ContentCachingResponseWrapper responseWrapper, HttpServletResponse response, String subscriptionId) {
        if (subscriptionViewRepoService.findBySubscriptionId(subscriptionId).getPlanType().equalsIgnoreCase("limited request per day")) {
            if (redisRequestCounter.hasKey(subscriptionId + LocalDate.now()).equals(Boolean.TRUE))
                redisRequestCounter.incrementValue(subscriptionId + LocalDate.now());
            else
                redisRequestCounter.setData(subscriptionId + LocalDate.now(), Long.parseLong("1"));
        } else {
            if (redisRequestCounter.hasKey(subscriptionId).equals(Boolean.TRUE))
                redisRequestCounter.incrementValue(subscriptionId);
            else
                redisRequestCounter.setData(subscriptionId, Long.parseLong("1"));
        }
        RequestResponseLog requestResponseLog = new RequestResponseLog();
        try {
            String requestBody = new String(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
            String responseBody = new String(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());
            requestResponseLog.setSubscriptionId(subscriptionId);
            requestResponseLog.setRequestMethod(request.getMethod());
            requestResponseLog.setRequestUrl(request.getRequestURL().toString());
            requestResponseLog.setRequestBody(requestBody);
            requestResponseLog.setResponseStatusCode(response.getStatus());
            requestResponseLog.setResponseBody(responseBody);
            requestResponseLogRepository.save(requestResponseLog);

        } catch (UnsupportedEncodingException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Optional<Company> findCompany(String username) {

        return companyRepoService.findByCompanyName(username);
    }

    @Override
    public String generateApi(HttpServletRequest request) {
        String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJ7+ov4Piiqf2PrvsMaeiehJPzvqb3cAkhYg1f5OU7gsDn+m4+MLJe/T1QUVAk3eVyxPzJ0lcqs+2mmlEqOe/tM0BHcNutjZgnoopIuqbwSMGlu0INyKMhHMt7+2Hvuz+mVkkv9Brh/LlW8G+6UpAkf26f1exxCAzH0dOz7NVEuwIDAQAB";
        byte[] encodedPb = Base64.getDecoder().decode(publickey.getBytes());
        X509EncodedKeySpec keySpecPb = new X509EncodedKeySpec(encodedPb);
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey publicKey = kf.generatePublic(keySpecPb);
            String token = request.getHeader("Authorization");
            if (token == null)
                throw new BadRequestException("not Authorized");
            token = token.substring(7);
            if (jwtService.extractAllClaims(token).get(subscribe) == null)
                throw new BadRequestException("not subscribed");
            byte[] subscriptionId = (jwtService.extractAllClaims(token).get(subscribe).toString() + "_" + jwtService.extractAllClaims(token).getExpiration()).getBytes();
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            subscriptionId = cipher.doFinal(subscriptionId);
            return Base64.getEncoder().encodeToString(subscriptionId);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 InvalidKeySpecException | BadPaddingException | InvalidKeyException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}

