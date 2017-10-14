package org.maroubra.pemsserver.bindings;

import com.github.joschi.jadconfig.JadConfig;
import com.github.joschi.jadconfig.RepositoryException;
import com.github.joschi.jadconfig.ValidationException;
import com.github.joschi.jadconfig.repositories.EnvironmentRepository;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoDatabase;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.maroubra.pemsserver.configuration.MongoDbConfiguration;

public class MongoBindings extends AbstractBinder{

    @Override
    protected void configure() {
        MongoDbConfiguration configuration = loadConfiguration();
        MongoClient client = MongoClients.create(configuration.connectionString);
        MongoDatabase db = client.getDatabase(configuration.databaseName);

        bind(db);
    }

    private static MongoDbConfiguration loadConfiguration() {
        MongoDbConfiguration mongoDbConfiguration = new MongoDbConfiguration();
        JadConfig jadConfig = new JadConfig(new EnvironmentRepository("PEMS_"), mongoDbConfiguration);

        try {
            jadConfig.process();
        } catch (RepositoryException ex) {

        } catch (ValidationException ex) {

        }

        return mongoDbConfiguration;
    }
}
