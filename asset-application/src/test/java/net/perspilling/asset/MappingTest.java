package net.perspilling.asset;

import net.perspilling.asset.api.Address;
import net.perspilling.asset.core.AddressEntity;
import net.perspilling.asset.resources.mapper.AddressMapper;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertEquals;

public class MappingTest {
    @Test
    public void mapAddress() {
        AddressEntity addressEntity =
                new AddressEntity.Builder()
                        .streetName("Landingsveien").streetName("112")
                        .postCode("0123").areaName("Oslo").build();

        Address address = AddressMapper.mapToAddress(addressEntity);

        assertNotNull(address);
        assertEquals(addressEntity.getStreetName(), address.getStreetName());
        assertEquals(addressEntity.getHouseNumber(), address.getHouseNumber());
        assertEquals(addressEntity.getPostCode(), address.getPostCode());
        assertEquals(addressEntity.getAreaName(), address.getAreaName());
    }
}
