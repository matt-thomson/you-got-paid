package com.gocardless;

import com.gocardless.resources.WebhookResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class YouGotPaidApplication extends Application<YouGotPaidConfiguration> {
    @Override
    public void run(YouGotPaidConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new WebhookResource());
    }

    public static void main(String[] args) throws Exception {
        new YouGotPaidApplication().run(args);
    }
}
