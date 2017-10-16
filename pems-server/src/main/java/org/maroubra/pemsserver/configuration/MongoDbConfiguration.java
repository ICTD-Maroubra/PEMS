package org.maroubra.pemsserver.configuration;

import com.github.joschi.jadconfig.Parameter;

public class MongoDbConfiguration {
    @Parameter(value = "mongodb_connection")
    public String connectionString = "mongodb://0.0.0.0:27017";

    @Parameter(value = "mongodb_database")
    public String databaseName = "pems";
}
