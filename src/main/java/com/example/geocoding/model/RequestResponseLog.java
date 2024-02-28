package com.example.geocoding.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
public class RequestResponseLog {
    @Id
    private String id;
    private String subscriptionId;
    @CreatedDate
    private LocalDate date;
    private String requestMethod;
    private String requestUrl;
    private String requestBody;
    private int responseStatusCode;
    private String responseBody;

}
