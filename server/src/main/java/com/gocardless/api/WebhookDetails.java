package com.gocardless.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookDetails {
    private final String cause;

    public WebhookDetails(@JsonProperty("cause") String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }

    @Override
    public String toString() {
        return "WebhookDetails{" +
                "cause='" + cause + '\'' +
                '}';
    }
}
