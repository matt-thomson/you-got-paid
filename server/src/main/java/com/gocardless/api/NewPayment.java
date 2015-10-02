package com.gocardless.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewPayment {
    private final int amount;
    private final String givenName;
    private final String familyName;

    @JsonCreator
    public NewPayment(@JsonProperty("amount") int amount,
                      @JsonProperty("given_name") String givenName,
                      @JsonProperty("family_name") String familyName) {
        this.amount = amount;
        this.givenName = givenName;
        this.familyName = familyName;
    }

    public int getAmount() {
        return amount;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    @Override
    public String toString() {
        return "NewPayment{" +
                "amount=" + amount +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                '}';
    }
}
