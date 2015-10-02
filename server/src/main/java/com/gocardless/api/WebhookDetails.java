package com.gocardless.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhookDetails {
    private final String cause;

    @JsonCreator
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
