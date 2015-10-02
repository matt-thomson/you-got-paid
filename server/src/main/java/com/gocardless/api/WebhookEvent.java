package com.gocardless.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookEvent {
    private final WebhookLinks links;
    private final WebhookDetails details;

    @JsonCreator
    public WebhookEvent(@JsonProperty("links") WebhookLinks links,
                        @JsonProperty("details") WebhookDetails details) {
        this.links = links;
        this.details = details;
    }

    public WebhookLinks getLinks() {
        return links;
    }

    public WebhookDetails getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "WebhookEvent{" +
                "links=" + links +
                ", details=" + details +
                '}';
    }
}
