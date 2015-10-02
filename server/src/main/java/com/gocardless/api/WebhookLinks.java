package com.gocardless.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookLinks {
    private final String payment;

    public WebhookLinks(@JsonProperty("payment") String payment) {
        this.payment = payment;
    }

    public String getPayment() {
        return payment;
    }

    @Override
    public String toString() {
        return "WebhookLinks{" +
                "payment='" + payment + '\'' +
                '}';
    }
}
