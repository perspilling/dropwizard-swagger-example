package net.perspilling.asset.db;

import io.dropwizard.hibernate.AbstractDAO;
import net.perspilling.asset.core.AssetEntity;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Per Spilling
 */
public class AssetDaoHib extends AbstractDAO<AssetEntity> implements AssetDao {
    private static final Logger LOG = LoggerFactory.getLogger(AssetDaoHib.class);

    public AssetDaoHib(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public AssetEntity findById(Long id) {
        return get(id);
    }

    @Override
    public AssetEntity save(AssetEntity assetEntity) {
        return persist(assetEntity);
    }

    private static final String hqlFindAll = "from net.perspilling.asset.core.AssetEntity";

    @Override
    public List<AssetEntity> findAll() {
        return list(currentSession().createQuery(hqlFindAll));
    }

    @Override
    public void delete(Long id) {
        Object o = currentSession().load(AssetEntity.class, id);
        currentSession().delete(o);
    }
}
