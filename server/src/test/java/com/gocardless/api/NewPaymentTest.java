package com.gocardless.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class NewPaymentTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void shouldSerializeNewPayment() throws IOException {
        NewPayment newPayment = new NewPayment(123, "Tom", "Jones");
        String expected = MAPPER.writeValueAsString(MAPPER.readValue(fixture("fixtures/new_payment.json"), NewPayment.class));

        assertThat(MAPPER.writeValueAsString(newPayment)).isEqualTo(expected);
    }
}
