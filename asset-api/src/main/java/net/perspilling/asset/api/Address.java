package net.perspilling.asset.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

/**
 * @author Per Spilling
 */
public class Address {

    private String streetName;

    private String houseNumber;

    private String postCode;

    private String areaName;

    public Address() {
        // Jackson deserialization
    }

    public Address(String streetName, String houseNumber, String postCode, String areaName) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
        this.areaName = areaName;
    }

    @JsonProperty
    @Length(max = 40)
    public String getStreetName() {
        return streetName;
    }

    @JsonProperty
    @Length(max = 8)
    public String getHouseNumber() {
        return houseNumber;
    }

    @JsonProperty
    @Length(max = 8)
    public String getPostCode() {
        return postCode;
    }

    @JsonProperty
    @Length(max = 40)
    public String getAreaName() {
        return areaName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }

        final Address that = (Address) o;

        return Objects.equals(this.streetName, that.streetName) &&
                Objects.equals(this.houseNumber, that.houseNumber) &&
                Objects.equals(this.postCode, that.postCode) &&
                Objects.equals(this.areaName, that.areaName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, houseNumber, postCode, areaName);
    }
}
