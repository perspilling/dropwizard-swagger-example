package net.perspilling.asset;

import net.perspilling.asset.api.Address;
import net.perspilling.asset.api.Asset;
import net.perspilling.asset.api.Saying;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This test requires that the asset-application has been started. See the README.md file in the project root
 * directory on how to do that.
 */
public class AssetClientIntegrationTest {
    private static String ASSET_SERVER_BASE_URL = "http://localhost:9000/api";

    private static String HELLO_ENDPOINT = ASSET_SERVER_BASE_URL + "/hello-world";

    private static String ASSET_ENDPOINT = ASSET_SERVER_BASE_URL + "/asset";

    private static WebTarget helloTarget = ClientBuilder.newClient().target(HELLO_ENDPOINT);

    private static WebTarget assetTarget = ClientBuilder.newClient().target(ASSET_ENDPOINT);

    @BeforeClass
    public static void seedDb() {
        List<Asset> result = assetTarget.request().get(new GenericType<List<Asset>>() {});

        if (result.isEmpty()) {
            saveAsset(new Asset(getRandomSerialNumber(), "Model 1000", new Address("Storgata", "10", "1234", "Oslo")));
            saveAsset(new Asset(getRandomSerialNumber(), "Model 1000", new Address("Frognerveien", "55", "1240", "Oslo")));
            saveAsset(new Asset(getRandomSerialNumber(), "Model 2000", new Address("Skansebakken", "42", "1011", "Oslo")));
        }
    }

    private static Asset saveAsset(Asset asset) {
        return assetTarget.request().post(Entity.json(asset)).readEntity(Asset.class);
    }

    @Test
    public void helloTest() {
        Saying result = helloTarget.queryParam("name", "dropwizard").request().get(Saying.class);
        assertEquals(result.getContent(), "Hello, dropwizard!");
    }

    @Test
    public void assetGetAllTest() {
        List<Asset> result = assetTarget.request().get(new GenericType<List<Asset>>() {});
        assertTrue(result.size() > 0);
        Asset asset1 = result.get(0);
        assertTrue(asset1.getSerialNumber() != null);
        assertTrue(asset1.getModelName() != null);
        assertTrue(asset1.getAddress() != null);
    }

    @Test
    public void assetCreateAndUpdateTest() {
        // create
        Asset asset = new Asset(getRandomSerialNumber(), "Model 3000", null);
        Asset a = saveAsset(asset);
        assertThat(a.getSerialNumber()).isEqualTo(asset.getSerialNumber());
        assertThat(a.getSerialNumber()).isEqualTo(asset.getSerialNumber());

        // update; add an address
        asset = new Asset(a.getId(), a.getSerialNumber(), a.getModelName(), new Address("Storgata", "10", "0123", "Oslo"));
        a = saveAsset(asset);
        assertThat(a.getAddress()).isEqualTo(asset.getAddress());
    }


    private static String getRandomSerialNumber() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
