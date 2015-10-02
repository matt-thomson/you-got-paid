package com.gocardless;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class YouGotPaidConfiguration extends Configuration {
    @JsonProperty
    @NotNull
    @Valid
    private GoCardlessFactory goCardless;

    public GoCardlessFactory getGoCardless() {
        return goCardless;
    }
}
