package net.perspilling.asset.core;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * The domain class for addresses.
 *
 * @author Per Spilling
 */
@Entity
@Table(name = "addresses")
public class AddressEntity {

    public static class Builder {
        private Long assetId; // PK & FK
        private String streetName;
        private String houseNumber;
        private String postCode;
        private String areaName;

        public Builder() {
        }

        public Builder(Long assetId) {
            this.assetId = assetId;
        }

        public Builder streetName(String streetName) {
            this.streetName = streetName;
            return this;
        }

        public Builder houseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public Builder postCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        public Builder areaName(String areaName) {
            this.areaName = areaName;
            return this;
        }

        public AddressEntity build() {
            return new AddressEntity(this);
        }
    }

    @Id
    @GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "asset"))
    @GeneratedValue(generator = "gen")
    private Long assetId; // PK & FK

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assetId")
    private AssetEntity asset;

    @Length(max = 40)
    private String streetName;

    @Length(max = 8)
    private String houseNumber;

    @Length(max = 8)
    private String postCode;

    @Length(max = 40)
    private String areaName;

    public AddressEntity() {
        // required for Hibernate
    }

    private AddressEntity(Builder builder) {
        this.assetId = builder.assetId;
        this.streetName = builder.streetName;
        this.houseNumber = builder.houseNumber;
        this.postCode = builder.postCode;
        this.areaName = builder.areaName;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public AssetEntity getAsset() {
        return asset;
    }

    public void setAsset(AssetEntity asset) {
        this.asset = asset;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressEntity)) {
            return false;
        }

        final AddressEntity that = (AddressEntity) o;

        return Objects.equals(this.assetId, that.assetId) &&
                Objects.equals(this.streetName, that.streetName) &&
                Objects.equals(this.houseNumber, that.houseNumber) &&
                Objects.equals(this.postCode, that.postCode) &&
                Objects.equals(this.areaName, that.areaName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetId, streetName, houseNumber, postCode, areaName);
    }
}
