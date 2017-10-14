package org.maroubra.pemsserver.monitoring;

import io.reactivex.Flowable;

public abstract class AbstractSensor {

    protected abstract boolean start();
    protected abstract boolean stop();
    protected abstract Flowable<SensorLog> logs();
}
