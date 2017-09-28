package org.maroubra.pemsserver.database;

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoDatabase;

public interface MongoConnection {
    MongoClient connect();

    MongoDatabase getDatabase();
}
