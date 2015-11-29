package net.perspilling.asset.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * The representation (DTO) of an asset entity.
 *
 * @author Per Spilling
 */
public class Asset {
    private Long id;

    @Size(min = 4, max = 10)
    @NotEmpty   // may not be null or blank
    private String serialNumber;

    @Length(max = 50)
    @NotEmpty
    private String modelName;

    private Address address;

    public Asset() {
        // Jackson deserialization
    }

    public Asset(String serialNumber, String modelName, Address address) {
        this.serialNumber = serialNumber;
        this.modelName = modelName;
        this.address = address;
    }

    public Asset(Long id, String serialNumber, String modelName, Address address) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.modelName = modelName;
        this.address = address;
    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonProperty
    public String getSerialNumber() {
        return serialNumber;
    }

    @JsonProperty
    public String getModelName() {
        return modelName;
    }

    @JsonProperty
    public Address getAddress() {
        return address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Asset)) {
            return false;
        }

        final Asset that = (Asset) o;

        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.serialNumber, that.serialNumber) &&
                Objects.equals(this.modelName, that.modelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, modelName);
    }
}
