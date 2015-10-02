package com.gocardless.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/webhooks")
public class WebhookResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebhookResource.class);

    @POST
    public void handleWebhook(String body) {
        LOGGER.info("Received webhook: {}", body);
    }
}
