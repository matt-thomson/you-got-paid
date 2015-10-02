package com.gocardless.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookLinks {
    private final String payment;

    @JsonCreator
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
