package com.gocardless.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewPayment {
    private final int amount;
    private final String name;

    @JsonCreator
    public NewPayment(@JsonProperty("amount") int amount,
                      @JsonProperty("name") String name) {
        this.amount = amount;
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "NewPayment{" +
                "amount=" + amount +
                ", name='" + name + '\'' +
                '}';
    }
}
