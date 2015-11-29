package net.perspilling.asset;

import io.dropwizard.jersey.validation.ValidationErrorMessage;
import io.dropwizard.testing.junit.ResourceTestRule;
import net.perspilling.asset.api.Address;
import net.perspilling.asset.api.Asset;
import net.perspilling.asset.db.AssetDaoDummyImpl;
import net.perspilling.asset.resources.AssetResource;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Per Spilling
 */
public class AssetEndpointTest {

    /**
     * The ResourceTestRule will simulate a resoure endpoint. Using @Rule will re-set this for every test.
     * @ClassRule can be used to only do this once for the test class (the variable must then be static)
     */
    @Rule
    public final ResourceTestRule resourceTestRule =
            ResourceTestRule.builder()
                            .addResource(new AssetResource(new AssetDaoDummyImpl()))
                            .build();

    private WebTarget assetEndpoint() {
        return resourceTestRule.client().target("/asset");
    }

    @Test
    public void getAllAssets() throws IOException {
        // AssetDaoDummyImpl seeds the "DB" with some assets
        List<Asset> result = assetEndpoint().request().get(new GenericType<List<Asset>>(){});

        assertThat(result.size()).isGreaterThan(0);
        Asset asset1 = result.get(0);
        assertThat(asset1.getSerialNumber()).isNotEmpty();
        assertThat(asset1.getModelName()).isNotEmpty();
        assertThat(asset1.getAddress()).isInstanceOf(Address.class);
    }

    @Test
    public void assetSaveAndUpdateTest() {
        Asset asset = new Asset(getRandomSerialNumber(), "Model3000", null);

        // save
        Asset newAsset = assetEndpoint().request().post(Entity.json(asset)).readEntity(Asset.class);

        assertThat(newAsset.getSerialNumber()).isEqualTo(asset.getSerialNumber());
        assertThat(newAsset.getModelName()).isEqualTo(asset.getModelName());
        assertThat(newAsset.getId()).isNotNull();

        // update
        asset = new Asset(newAsset.getId(), newAsset.getSerialNumber(), "Model3001", null);
        newAsset = assetEndpoint().request().post(Entity.json(asset)).readEntity(Asset.class);

        assertThat(newAsset.getSerialNumber()).isEqualTo(asset.getSerialNumber());
        assertThat(newAsset.getModelName()).isEqualTo(asset.getModelName());
    }

    @Test
    public void assetPostWithIllegalNullValues() {
        Asset asset = new Asset(null, null, null);
        Response postResponse = assetEndpoint().request().post(Entity.json(asset));

        assertThat(postResponse.getStatus()).isEqualTo(422); // 422 = Unprocessable Entity

        // Check to make sure that errors are correct and human readable
        ValidationErrorMessage msg = postResponse.readEntity(ValidationErrorMessage.class);
        assertThat(msg.getErrors()).isNotEmpty();

        assertThat(msg.getErrors()).contains("modelName may not be empty");
        assertThat(msg.getErrors()).contains("serialNumber may not be empty");
    }

    @Test
    public void assetPostWithIllegalLengthValues() {
        Asset asset = new Asset("012345678901", "Model101", null);
        Response postResponse = assetEndpoint().request().post(Entity.json(asset));

        assertThat(postResponse.getStatus()).isEqualTo(422); // 422 = Unprocessable Entity

        ValidationErrorMessage msg = postResponse.readEntity(ValidationErrorMessage.class);
        assertThat(msg.getErrors()).contains("serialNumber size must be between 4 and 10");

        asset = new Asset("123", "Model101", null);
        postResponse = assetEndpoint().request().post(Entity.json(asset));

        assertThat(postResponse.getStatus()).isEqualTo(422);

        msg = postResponse.readEntity(ValidationErrorMessage.class);
        assertThat(msg.getErrors()).contains("serialNumber size must be between 4 and 10");
    }

    @Test
    public void assetGet() {
        Long id = 1L;
        Asset asset = assetEndpoint().path(id.toString()).request().get().readEntity(Asset.class);
        assertThat(asset).isNotNull();
    }

    @Test
    public void assetDelete() {
        List<Asset> assets = assetEndpoint().request().get(new GenericType<List<Asset>>(){});

        Long idToDelete = 1L;
        assetEndpoint().path(idToDelete.toString()).request().delete();

        List<Asset> assets2 = assetEndpoint().request().get(new GenericType<List<Asset>>(){});
        assertThat(assets.size()).isGreaterThan(assets2.size());
    }

    private String getRandomSerialNumber() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
