package org.maroubra.pemsserver.configuration;

import com.mongodb.ConnectionString;

public class MongoDbConfiguration {

    public ConnectionString getConnectionString() {
        return new ConnectionString("");
    }
}
