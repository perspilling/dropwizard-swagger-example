package net.perspilling.asset.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * The representation (DTO) of an asset entity.
 *
 * @author Per Spilling
 */
@ApiModel
public class Asset {
    private Long id;

    @Size(min = 4, max = 10)
    @NotEmpty   // may not be null or blank
    @ApiModelProperty(required = true, value = "The serialnumber of the asset.")
    private String serialNumber;

    @Length(max = 50)
    @NotEmpty
    @ApiModelProperty(required = true, value = "The model name of the asset.")
    private String modelName;

    @ApiModelProperty(required = false, value = "The address where the asset is installed.")
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
