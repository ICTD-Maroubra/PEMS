package org.maroubra.pemsserver.monitoring.sensortag;

import io.reactivex.Flowable;
import org.maroubra.pemsserver.monitoring.AbstractSensor;
import org.maroubra.pemsserver.monitoring.SensorLog;

public class SensortagSensor extends AbstractSensor {

    @Override
    protected boolean start() {
        return false;
    }

    @Override
    protected boolean stop() {
        return false;
    }

    @Override
    protected Flowable<SensorLog> logs() {
        return null;
    }
}
