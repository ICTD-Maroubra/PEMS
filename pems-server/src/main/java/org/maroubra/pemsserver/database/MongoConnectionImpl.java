package org.maroubra.pemsserver.database;

import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;
import org.maroubra.pemsserver.configuration.MongoDbConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static com.google.common.base.Strings.isNullOrEmpty;

public class MongoConnectionImpl implements MongoConnection {

    private final Logger logger = LoggerFactory.getLogger(MongoConnectionImpl.class);
    private final ConnectionString connectionString;

    private MongoClient mongoClient = null;
    private MongoDatabase mongoDatabase = null;

    @Inject
    public MongoConnectionImpl(final MongoDbConfiguration configuration) {
        this(configuration.getConnectionString());
    }

    MongoConnectionImpl(ConnectionString mongoClientSettings) {
        this.connectionString = mongoClientSettings;
    }

    @Override
    public MongoClient connect() {
        if (mongoClient == null) {
            final String dbName = connectionString.getDatabase();
            if (isNullOrEmpty(dbName)) {
                logger.error("The MongoDB database name must not be null or empty (mongodb_uri was: {})", connectionString);
                throw new RuntimeException("MongoDB database name is missing.");
            }

            mongoClient = MongoClients.create(connectionString);
            mongoDatabase = mongoClient.getDatabase(dbName);
        }

        return mongoClient;
    }

    @Override
    public MongoDatabase getDatabase() {
        return mongoDatabase;
    }
}
