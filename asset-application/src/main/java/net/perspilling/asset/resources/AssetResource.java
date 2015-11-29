package net.perspilling.asset.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import io.swagger.annotations.Api;
import net.perspilling.asset.api.Asset;
import net.perspilling.asset.core.AssetEntity;
import net.perspilling.asset.db.AssetDao;
import net.perspilling.asset.resources.mapper.AddressMapper;
import net.perspilling.asset.resources.mapper.AssetMapper;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Per Spilling
 */
@Path("/asset")
@Api("/asset")
@Produces(MediaType.APPLICATION_JSON)
public class AssetResource {

    private AssetDao assetDao;

    public AssetResource(AssetDao assetDao) {
        this.assetDao = assetDao;
    }

    @GET @Path("/{id}")
    @UnitOfWork
    @Timed
    public Asset findById(@PathParam("id") LongParam id) {
        AssetEntity assetEntity = assetDao.findById(id.get());
        if (assetEntity == null) {
            throw new NotFoundException("No such asset");
        }
        return AssetMapper.mapToAsset(assetEntity);
    }

    @POST
    @UnitOfWork
    @Timed
    public Asset save(@Valid Asset asset) {
        return AssetMapper.mapToAsset(assetDao.save(
                new AssetEntity(asset.getId(), asset.getSerialNumber(), asset.getModelName(), AddressMapper.mapToAddressEntity(asset.getAddress()))));
    }

    @GET
    @UnitOfWork
    @Timed
    public List<Asset> findAll() {
        return AssetMapper.mapToAssetList(assetDao.findAll());
    }

    @DELETE @Path("/{id}")
    @UnitOfWork
    public void delete(@PathParam("id") LongParam id) {
        assetDao.delete(id.get());
    }
}
