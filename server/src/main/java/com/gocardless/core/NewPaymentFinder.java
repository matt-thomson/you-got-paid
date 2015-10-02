package com.gocardless.core;

import com.gocardless.GoCardlessClient;
import com.gocardless.api.NewPayment;
import com.gocardless.api.Webhook;
import com.gocardless.api.WebhookEvent;
import com.gocardless.resources.Customer;
import com.gocardless.resources.CustomerBankAccount;
import com.gocardless.resources.Mandate;
import com.gocardless.resources.Payment;
import com.google.common.base.Objects;

import java.util.stream.Stream;

public class NewPaymentFinder {
    private final GoCardlessClient goCardless;

    public NewPaymentFinder(GoCardlessClient goCardless) {
        this.goCardless = goCardless;
    }

    public Stream<NewPayment> find(Webhook webhook) {
        return webhook.getEvents().stream()
                .filter(event -> Objects.equal(event.getDetails().getCause(), "payment_created"))
                .map(this::eventToPayment);
    }

    private NewPayment eventToPayment(WebhookEvent event) {
        Payment payment = goCardless.payments().get(event.getLinks().getPayment()).execute();
        Mandate mandate = goCardless.mandates().get(payment.getLinks().getMandate()).execute();
        CustomerBankAccount bankAccount = goCardless.customerBankAccounts().get(mandate.getLinks().getCustomerBankAccount()).execute();
        Customer customer = goCardless.customers().get(bankAccount.getLinks().getCustomer()).execute();

        String customerName = String.format("%s %s", customer.getGivenName(), customer.getFamilyName());
        return new NewPayment(payment.getAmount(), customerName);
    }
}
