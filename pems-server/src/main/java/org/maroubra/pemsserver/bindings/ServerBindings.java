package org.maroubra.pemsserver.bindings;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.maroubra.pemsserver.database.MongoConnection;
import org.maroubra.pemsserver.database.MongoConnectionImpl;

import javax.inject.Singleton;

public class ServerBindings extends AbstractBinder {
    @Override
    protected void configure() {
        bind(MongoConnectionImpl.class).to(MongoConnection.class).in(Singleton.class);
    }
}
