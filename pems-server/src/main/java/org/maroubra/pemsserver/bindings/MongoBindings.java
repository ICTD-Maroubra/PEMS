package org.maroubra.pemsserver.bindings;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoDatabase;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class MongoBindings extends AbstractBinder{

    @Override
    protected void configure() {
        MongoClient client = MongoClients.create();
        MongoDatabase db = client.getDatabase("");
    }
}
