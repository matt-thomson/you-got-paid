package com.gocardless;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gocardless.GoCardlessClient.Environment;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class GoCardlessFactory {
    @JsonProperty
    @NotEmpty
    private String accessToken;

    @JsonProperty
    private String webhookSecret;

    @JsonProperty
    @NotNull
    private Environment environment;

    public GoCardlessClient buildClient() {
        return GoCardlessClient.create(accessToken, environment);
    }
}
