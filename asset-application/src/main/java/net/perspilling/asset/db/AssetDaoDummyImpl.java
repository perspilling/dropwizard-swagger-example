
package net.perspilling.asset.db;

import net.perspilling.asset.core.AddressEntity;
import net.perspilling.asset.core.AssetEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Per Spilling
 */
public class AssetDaoDummyImpl implements AssetDao {

    private static final Map<Long, AssetEntity> assets = Collections.synchronizedMap(new HashMap<Long, AssetEntity>());

    private static AtomicLong assetCounter = new AtomicLong();

    private static AtomicLong addrCounter = new AtomicLong();

    public AssetDaoDummyImpl() {
        if (assets.isEmpty()) {
            seedSomeData();
        }
    }

    @Override
    public AssetEntity findById(Long id) {
        return assets.get(id);
    }

    @Override
    public AssetEntity save(AssetEntity assetEntity) {
        Long id = null;
        if (assetEntity.getId() == null) {
            id = addAsset(assetEntity);
        } else {
            updateAsset(assetEntity);
            id = assetEntity.getId();
        }
        return assets.get(id);
    }

    @Override
    public List<AssetEntity> findAll() {
        return new ArrayList<AssetEntity>(assets.values());
    }

    @Override
    public void delete(Long id) {
        synchronized (assets) {
            if (assets.containsKey(id)) {
                assets.remove(id);
            }
        }
    }

    private String getRandomSerialNumber() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private void seedSomeData() {
        addAsset(new AssetEntity(getRandomSerialNumber(), "Model1000",
                new AddressEntity.Builder()
                        .streetName("Storgata").houseNumber("10").postCode("1234").areaName("Oslo").build()));
        addAsset(new AssetEntity(getRandomSerialNumber(), "Model1000",
                new AddressEntity.Builder()
                        .streetName("Frognerveien").houseNumber("55").postCode("1240").areaName("Oslo").build()));
        addAsset(new AssetEntity(getRandomSerialNumber(), "Model2000",
                new AddressEntity.Builder()
                        .streetName("Skansebakken").houseNumber("11").postCode("1011").areaName("Oslo").build()));
    }

    private Long addAsset(AssetEntity asset) {
        synchronized (assets) {
            Long assetPk = assetCounter.incrementAndGet();
            AddressEntity addressEntity = null;
            if (asset.getAddress() != null) {
                AddressEntity a = asset.getAddress();
                addressEntity = new AddressEntity.Builder(assetPk)
                        .streetName(a.getStreetName()).houseNumber(a.getHouseNumber())
                        .postCode(a.getPostCode()).areaName(a.getAreaName()).build();
            }
            assets.put(assetPk, new AssetEntity(assetPk, asset.getSerialNumber(), asset.getModelName(), addressEntity));
            return assetPk;
        }
    }

    private void updateAsset(AssetEntity asset) {
        synchronized (assets) {
            assets.put(asset.getId(), asset);
        }
    }
}
