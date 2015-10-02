package com.gocardless.resources;

import com.gocardless.api.Webhook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/webhooks")
public class WebhookResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebhookResource.class);

    @POST
    public void handleWebhook(Webhook webhook) {
        LOGGER.info("Received webhook: {}", webhook);
    }
}
