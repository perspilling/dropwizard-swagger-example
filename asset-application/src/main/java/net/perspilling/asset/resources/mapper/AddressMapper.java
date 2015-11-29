package net.perspilling.asset.resources.mapper;

import net.perspilling.asset.api.Address;
import net.perspilling.asset.core.AddressEntity;

/**
 * @author Per Spilling
 */
public class AddressMapper {

    public static Address mapToAddress(AddressEntity addressEntity) {
        Address address = null;
        if (addressEntity != null) {
            address = new Address(
                    addressEntity.getStreetName(), addressEntity.getHouseNumber(),
                    addressEntity.getPostCode(), addressEntity.getAreaName());
        }
        return address;
    }

    public static AddressEntity mapToAddressEntity(Address address) {
        AddressEntity addressEntity = null;
        if (address != null) {
            addressEntity = new AddressEntity.Builder()
                    .streetName(address.getStreetName())
                    .houseNumber(address.getHouseNumber())
                    .postCode(address.getPostCode())
                    .areaName(address.getAreaName())
                    .build();
        }
        return addressEntity;
    }
}
