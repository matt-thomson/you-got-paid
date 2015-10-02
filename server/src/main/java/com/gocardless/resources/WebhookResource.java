package com.gocardless.resources;

import com.gocardless.api.Webhook;
import com.gocardless.core.NewPaymentBroadcaster;
import com.gocardless.core.NewPaymentFinder;
import com.gocardless.ws.BroadcastSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/webhooks")
public class WebhookResource {
    private final NewPaymentFinder newPaymentFinder;
    private final NewPaymentBroadcaster newPaymentBroadcaster;

    public WebhookResource(NewPaymentFinder newPaymentFinder,
                           NewPaymentBroadcaster newPaymentBroadcaster) {
        this.newPaymentFinder = newPaymentFinder;
        this.newPaymentBroadcaster = newPaymentBroadcaster;
    }

    @POST
    public void handleWebhook(Webhook webhook) {
        newPaymentFinder.find(webhook).forEach(newPaymentBroadcaster::broadcast);
    }
}
