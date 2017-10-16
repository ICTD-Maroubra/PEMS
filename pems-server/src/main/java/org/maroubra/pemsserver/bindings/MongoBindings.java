package org.maroubra.pemsserver.bindings;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoDatabase;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.maroubra.pemsserver.configuration.Configuration;
import org.maroubra.pemsserver.configuration.MongoDbConfiguration;

public class MongoBindings extends AbstractBinder{

    @Override
    protected void configure() {
        MongoDbConfiguration configuration = Configuration.getMongoDbConfiguration();
        MongoClient client = MongoClients.create(configuration.connectionString);
        MongoDatabase db = client.getDatabase(configuration.databaseName);

        bind(db);
    }
}
