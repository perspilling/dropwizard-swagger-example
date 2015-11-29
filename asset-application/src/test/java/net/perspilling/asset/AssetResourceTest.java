package net.perspilling.asset;

import net.perspilling.asset.api.Asset;
import net.perspilling.asset.db.AssetDaoDummyImpl;
import net.perspilling.asset.resources.AssetResource;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AssetResourceTest {

    private AssetResource resource;

    @Before
    public void setup() {
        resource = new AssetResource(new AssetDaoDummyImpl());
    }

    @Test
    public void idStartsAtOne() {
        Asset asset = resource.findAll().get(0);
        assertThat(asset.getId()).isEqualTo(1);
    }
}
