package org.maroubra.pemsserver.bindings;

import com.google.inject.AbstractModule;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.maroubra.pemsserver.configuration.Configuration;
import org.maroubra.pemsserver.configuration.MongoDbConfiguration;
import org.maroubra.pemsserver.database.*;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoBindings extends AbstractModule {

    @Override
    protected void configure() {
        MongoDbConfiguration configuration = Configuration.getMongoDbConfiguration();

        CodecRegistry pojoCodecRegistry = fromRegistries(
                CodecRegistries.fromCodecs(new LocalDateTimeCodec()),
                MongoClients.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder()
                        .automatic(true)
                        .register(SensorConfig.class, SensorLog.class)
                        .build()),
                CodecRegistries.fromCodecs(new NaiveObjectCodec(MongoClients.getDefaultCodecRegistry())));

        MongoClient client = MongoClients.create(configuration.connectionString);
        MongoDatabase db = client.getDatabase(configuration.databaseName).withCodecRegistry(pojoCodecRegistry);

        bind(MongoDatabase.class).toInstance(db);
    }
}
