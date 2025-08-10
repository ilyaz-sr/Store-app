package com.codewithmosh.store.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    @Value("sk_test_51Rt3q6RrEOR3LONyXyJycoN3s7gZS9sDAqmDfgvGXQuZkbqWdatQ61t6v6t5O81TTvy30xmRi38bwS2MmUdVMZ5a00FbO6tDM3")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }
}