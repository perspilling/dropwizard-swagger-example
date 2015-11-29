package net.perspilling.asset.core;

import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * The domain class for assets.
 *
 * @author Per Spilling
 */
@Entity
@Table(name = "assets")
public class AssetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK in DB

    @Length(max = 10)
    @Column(name = "serialNumber", nullable = false)
    private String serialNumber; // natural id

    @Length(max = 50)
    @Column(name = "modelName", nullable = false)
    private String modelName;

    @OneToOne( mappedBy = "asset", cascade = {CascadeType.ALL} )
    private AddressEntity address;

    public AssetEntity() {
    }

    public AssetEntity(String serialNumber, String modelName, AddressEntity address) {
        this.serialNumber = serialNumber;
        this.modelName = modelName;
        this.address = address;
        if (address != null) address.setAsset(this);
    }

    public AssetEntity(Long id, String serialNumber, String modelName, AddressEntity address) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.modelName = modelName;
        this.address = address;
        if (address != null) address.setAsset(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetEntity)) {
            return false;
        }

        final AssetEntity that = (AssetEntity) o;

        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.serialNumber, that.serialNumber) &&
                Objects.equals(this.modelName, that.modelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, modelName);
    }
}
