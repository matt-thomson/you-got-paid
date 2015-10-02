package com.gocardless.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class WebhookTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void shouldParseWebhook() throws IOException {
        Webhook webhook = MAPPER.readValue(fixture("fixtures/webhook.json"), Webhook.class);
        assertThat(webhook.getEvents()).hasSize(1);

        WebhookEvent event = webhook.getEvents().get(0);
        assertThat(event.getDetails().getCause()).isEqualTo("payment_created");
        assertThat(event.getLinks().getPayment()).isEqualTo("index_ID_123");
    }
}
