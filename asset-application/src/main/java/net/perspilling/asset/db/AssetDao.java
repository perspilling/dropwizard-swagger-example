package net.perspilling.asset.db;

import net.perspilling.asset.core.AssetEntity;

import java.util.List;

/**
 * @author Per Spilling
 */
public interface AssetDao   {

    AssetEntity findById(Long id);

    AssetEntity save(AssetEntity assetEntity);

    void delete(Long id);

    List<AssetEntity> findAll();
}
