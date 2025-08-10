package com.codewithmosh.store.repositories;

import com.codewithmosh.store.dtos.PaymentResult;
import com.codewithmosh.store.dtos.WebhookRequest;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.services.CheckoutSession;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);

}