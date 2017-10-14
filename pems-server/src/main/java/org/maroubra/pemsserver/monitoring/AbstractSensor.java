package org.maroubra.pemsserver.monitoring;

import io.reactivex.Flowable;
import org.maroubra.pemsserver.database.CollectionName;

@CollectionName("sensor")
public abstract class AbstractSensor {

    protected abstract boolean start();
    protected abstract boolean stop();
    protected abstract Flowable<SensorLog> logs();
}
