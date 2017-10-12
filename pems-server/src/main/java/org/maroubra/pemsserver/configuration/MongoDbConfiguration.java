package org.maroubra.pemsserver.configuration;

import com.mongodb.ConnectionString;

public class MongoDbConfiguration {

    public ConnectionString getConnectionString() {
        return new ConnectionString("mongodb://127.0.0.1:27017/local");
    }
}
