package net.perspilling.asset;


import net.perspilling.asset.api.Saying;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Client;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test that the application configuration is properly set up.
 */
public class AssetApplicationIntegrationTest {
    @Rule
    public final DropwizardAppRule<AssetConfiguration> RULE =
            new DropwizardAppRule<AssetConfiguration>(AssetApplication.class,
                    ResourceHelpers.resourceFilePath("asset-application.yml"));

    @Test
    public void runServerTest() {
        Client client = new JerseyClientBuilder().build();
        Saying result = client.target(
                String.format("http://localhost:%d/api/hello-world", RULE.getLocalPort()))
                              .queryParam("name", "dropwizard").request().get(Saying.class);
        assertThat(result.getContent()).isEqualTo("Hello, dropwizard!");
    }
}
