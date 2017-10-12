package org.maroubra.pemsserver.database;

import com.mongodb.Block;
import com.mongodb.async.client.*;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.maroubra.pemsserver.api.models.DeviceConfig;
import org.maroubra.pemsserver.bootstrap.Main;
import org.maroubra.pemsserver.configuration.MongoDbConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class PemsBaseDBService {

    private MongoConnectionImpl mongoConnection;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    public PemsBaseDBService (){

        mongoConnection = new MongoConnectionImpl(new MongoDbConfiguration());
        mongoClient = mongoConnection.connect();

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClients.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        mongoDatabase = mongoClient.getDatabase("test");
        mongoDatabase = mongoDatabase.withCodecRegistry(pojoCodecRegistry);

        mongoDatabase.createCollection("deviceConfig",(aVoid, throwable) -> throwable.printStackTrace());
    }

    public void insertDeviceConfig()
    {
        MongoCollection<DeviceConfig> deviceConfigMongoCollection = mongoDatabase.getCollection("deviceConfig",DeviceConfig.class);
        DeviceConfig deviceConfig = new DeviceConfig();
        deviceConfig.setName("SensorTag");

        deviceConfigMongoCollection.insertOne(deviceConfig, (aVoid, throwable) -> throwable.printStackTrace());

        Block<DeviceConfig> printBlock = new Block<DeviceConfig>() {
            @Override
            public void apply(final DeviceConfig person) {
                System.out.println(person);
            }
        };

        deviceConfigMongoCollection.find().forEach(printBlock, (aVoid, throwable) -> throwable.printStackTrace());




    }

}
