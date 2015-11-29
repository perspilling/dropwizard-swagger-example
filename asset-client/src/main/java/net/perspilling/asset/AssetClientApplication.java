/**
 * Copyright (c) Embriq AS. All rights reserved.
 * <p>
 * Created 22/11/15 17:45
 *
 * @author PerSpilling
 */
package net.perspilling.asset;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

public class AssetClientApplication extends Application<AssetClientConfiguration> {
    @Override
    public void run(AssetClientConfiguration config, Environment environment) throws Exception {

        final Client client = new JerseyClientBuilder(environment)
                .using(config.getJerseyClientConfiguration())
                .build(getName());
//        environment.jersey().register(new ExternalServiceResource(client));
    }
}
