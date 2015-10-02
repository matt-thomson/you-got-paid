package com.gocardless;

import com.gocardless.core.NewPaymentFinder;
import com.gocardless.resources.WebhookResource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class YouGotPaidApplication extends Application<YouGotPaidConfiguration> {
    @Override
    public void initialize(Bootstrap<YouGotPaidConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
    }

    @Override
    public void run(YouGotPaidConfiguration configuration, Environment environment) throws Exception {
        GoCardlessClient goCardless = configuration.getGoCardless().buildClient();
        NewPaymentFinder newPaymentFinder = new NewPaymentFinder(goCardless);

        environment.jersey().register(new WebhookResource(newPaymentFinder));
    }

    public static void main(String[] args) throws Exception {
        new YouGotPaidApplication().run(args);
    }
}
