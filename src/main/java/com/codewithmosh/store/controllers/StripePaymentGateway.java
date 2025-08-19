package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.PaymentResult;
import com.codewithmosh.store.dtos.WebhookRequest;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.entities.OrderItem;
import com.codewithmosh.store.exception.PaymentException;
import com.codewithmosh.store.repositories.PaymentGateway;
import com.codewithmosh.store.services.CheckoutSession;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StripePaymentGateway implements PaymentGateway {
//    @Value("${stripe.webhookSecretKey}")
//    private String webhookSecretKey;
    private WebhookRequest request;

    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try {
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:8080/success.html")
                    .setCancelUrl("http://localhost:8080/cancel.html")
                    .setPaymentIntentData(createPaymentIntent(order));

            order.getItems().forEach(item -> {
                var lineItem = createLineItem(item);
                builder.addLineItem(lineItem);
            });

            var session = Session.create(builder.build());
            return new CheckoutSession(session.getUrl());
        }
        catch (StripeException ex) {
            System.out.println(ex.getMessage());
            throw new PaymentException();
        }
    }

    @Override
    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {
        return Optional.empty();
    }

    private static SessionCreateParams.PaymentIntentData createPaymentIntent(Order order) {
        return SessionCreateParams.PaymentIntentData.builder()
                .putMetadata("order_id", order.getId().toString())
                .build();
    }

//    @Override
//    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {
//        try {
//            var payload = request.getPayload();
//            var signature = request.getHeaders().get("stripe-signature");
//            var event = Webhook.constructEvent(payload, signature, webhookSecretKey);
//
//            return switch (event.getType()) {
//                case "payment_intent.succeeded" ->
//                        Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.PAID));
//
//                case "payment_intent.payment_failed" ->
//                        Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.FAILED));
//
//                default -> Optional.empty();
//            };
//        } catch (SignatureVerificationException e) {
//            throw new PaymentException("Invalid signature");
//        }
//    }
//
    private Long extractOrderId(Event event) {
        var stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(
                () -> new PaymentException("Could not deserialize Stripe event. Check the SDK and API version.")
        );
        var paymentIntent = (PaymentIntent) stripeObject;
        return Long.valueOf(paymentIntent.getMetadata().get("order_id"));
    }

    private SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(createPriceData(item))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(
                        item.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                .setProductData(createProductData(item))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData createProductData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName())
                .build();
    }
}