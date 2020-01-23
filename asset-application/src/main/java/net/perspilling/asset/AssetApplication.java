package net.perspilling.asset;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import net.perspilling.asset.core.AddressEntity;
import net.perspilling.asset.core.AssetEntity;
import net.perspilling.asset.db.AssetDaoHib;
import net.perspilling.asset.health.TemplateHealthCheck;
import net.perspilling.asset.resources.AssetResource;
import net.perspilling.asset.resources.HelloWorldResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * This main-class will be used by the start_server.sh script to start the server. It can also be
 * started up in the IDE, just remember to set the correct working directory and provide the expected
 * parameters:
 *
 * Working directory: dropwizard-swagger-example/asset-application
 * Program arguments: server asset-application.yml
 *
 * @author Per Spilling
 */
public class AssetApplication extends Application<AssetConfiguration> {

    final static Logger log = LoggerFactory.getLogger(AssetApplication.class);


    // add entity classes to be mapped by Hibernate here
    private final HibernateBundle<AssetConfiguration> hibernateBundle = new HibernateBundle<AssetConfiguration>(
            AssetEntity.class, AddressEntity.class
    ) {
        @Override
        public DataSourceFactory getDataSourceFactory(AssetConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(String[] args) throws Exception {
        new AssetApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<AssetConfiguration> bootstrap) {

        bootstrap.addBundle(hibernateBundle);

        bootstrap.addBundle(new MigrationsBundle<AssetConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(AssetConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

        bootstrap.addBundle(new SwaggerBundle<AssetConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AssetConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(AssetConfiguration config, Environment environment) {

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(config.getTemplate());
        environment.healthChecks().register("template", healthCheck);

        environment.jersey().register(new HelloWorldResource(config.getTemplate(), config.getDefaultName()));

        final AssetDaoHib assetDaoHib = new AssetDaoHib(hibernateBundle.getSessionFactory());
        AssetResource assetResource = new AssetResource(assetDaoHib);
        environment.jersey().register(assetResource);

//        migrateDb(config, environment);  // using hibernate.hbm2ddl.auto: create-drop for now

        // Getting the following exception when trying to call DbSeeder:
        // Exception in thread "main" org.hibernate.HibernateException: No session currently bound to execution context
//        at org.hibernate.context.internal.ManagedSessionContext.currentSession(ManagedSessionContext.java:75)
//        at org.hibernate.internal.SessionFactoryImpl.getCurrentSession(SessionFactoryImpl.java:1014)
//        at io.dropwizard.hibernate.AbstractDAO.currentSession(AbstractDAO.java:36)
//        at net.perspilling.asset.db.AssetDaoHib.findAll(AssetDaoHib.java:35)
//        at net.perspilling.asset.db.DbSeeder.seedSomeData(DbSeeder.java:24)
//        new DbSeeder(hibernateBundle.getSessionFactory(), assetDaoHib).seedSomeData();
    }


    private void migrateDb(AssetConfiguration configuration, Environment environment) {
//        if (configuration.isMigrateSchemaOnStartup()) {
        log.info("Running schema migration");
        ManagedDataSource dataSource = createMigrationDataSource(configuration, environment);

        try (Connection connection = dataSource.getConnection()) {
            JdbcConnection conn = new JdbcConnection(connection);

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(conn);
            Liquibase liquibase = new Liquibase("migrations.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update("");

            log.info("Migration completed!");
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to migrate database", ex);
        } finally {
            try {
                dataSource.stop();
            } catch (Exception ex) {
                log.error("Unable to stop data source used to execute schema migration", ex);
            }
        }
//        }
//        else {
//            log.info("Skipping schema migration");
//        }
    }

    private ManagedDataSource createMigrationDataSource(AssetConfiguration configuration, Environment environment) {
        DataSourceFactory dataSourceFactory = configuration.getDataSourceFactory();
        return dataSourceFactory.build(environment.metrics(), "migration-ds");
    }

    @Override
    public String getName() {
        return "asset-application";  // name must match the name of the yaml config file
    }
}
