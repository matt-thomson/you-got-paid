package com.gocardless.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class Webhook {
    private final List<WebhookEvent> events;

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
