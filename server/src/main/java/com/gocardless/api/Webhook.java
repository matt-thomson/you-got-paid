package com.gocardless.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Webhook {
    private final List<WebhookEvent> events;

    @JsonCreator
    public Webhook(@JsonProperty("events") List<WebhookEvent> events) {
        this.events = ImmutableList.copyOf(events);
    }

    public List<WebhookEvent> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        return "Webhook{" +
                "events=" + events +
                '}';
    }
}
