package net.perspilling.asset.resources.mapper;

import net.perspilling.asset.api.Asset;
import net.perspilling.asset.core.AssetEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Per Spilling
 */
public class AssetMapper {

    public static List<Asset> mapToAssetList(List<AssetEntity> all) {
        List<Asset> assets = new ArrayList<Asset>();
        for (AssetEntity assetEntity : all) {
            assets.add(mapToAsset(assetEntity));
        }
        return assets;
    }

    public static Asset mapToAsset(AssetEntity assetEntity) {
        return new Asset(assetEntity.getId(), assetEntity.getSerialNumber(), assetEntity.getModelName(),
                AddressMapper.mapToAddress(assetEntity.getAddress()));
    }
}
