package org.maroubra.pemsserver.database;

import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;

public class MongoCollectionFactory {

    private final MongoDatabase mongoDatabase;

    public MongoCollectionFactory(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public <T> MongoCollection<T> getCollection(Class<T> clazz) {
        CollectionName collectionNameAnnotation = clazz.getAnnotation(CollectionName.class);
        if (collectionNameAnnotation == null) {
            throw new RuntimeException("Unable to determine collection for class " + clazz.getCanonicalName());
        }
        final String collectionName = collectionNameAnnotation.value();

        return mongoDatabase.getCollection(collectionName, clazz);
    }
}
