package org.maroubra.pemsserver.bindings;

import com.google.inject.AbstractModule;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoDatabase;
import org.maroubra.pemsserver.configuration.Configuration;
import org.maroubra.pemsserver.configuration.MongoDbConfiguration;

public class MongoBindings extends AbstractModule {

    @Override
    protected void configure() {
        MongoDbConfiguration configuration = Configuration.getMongoDbConfiguration();
        MongoClient client = MongoClients.create(configuration.connectionString);
        MongoDatabase db = client.getDatabase(configuration.databaseName);

        bind(MongoDatabase.class).toInstance(db);
    }
}
