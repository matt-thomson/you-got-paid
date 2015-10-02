package com.gocardless.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocardless.api.NewPayment;
import com.gocardless.ws.BroadcastSocket;

public class NewPaymentBroadcaster {
    private final ObjectMapper mapper;

    public NewPaymentBroadcaster(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void broadcast(NewPayment newPayment) {
        String json;

        try {
            json = mapper.writeValueAsString(newPayment);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }

        BroadcastSocket.broadcast(json);
    }
}
