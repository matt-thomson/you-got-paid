package com.gocardless.resources;

import com.gocardless.api.Webhook;
import com.gocardless.core.NewPaymentFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/webhooks")
public class WebhookResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebhookResource.class);

    private final NewPaymentFinder newPaymentFinder;

    public WebhookResource(NewPaymentFinder newPaymentFinder) {
        this.newPaymentFinder = newPaymentFinder;
    }

    @POST
    public void handleWebhook(Webhook webhook) {
        newPaymentFinder.find(webhook).forEach(newPayment -> LOGGER.info("Got new payment [{}]", newPayment));
    }
}
