package org.maroubra.pemsserver.monitoring.sensortag;

import io.reactivex.Flowable;
import org.maroubra.pemsserver.monitoring.AbstractSensor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothDevice;

public class SensortagSensor extends AbstractSensor {

    private final SensortagSensorConfig config;
    private final BluetoothDevice sensortagDevice;

    public SensortagSensor(SensortagSensorConfig config, BluetoothDevice sensortagDevice) {
        this.config = config;
        this.sensortagDevice = sensortagDevice;
    }

    @Override
    protected boolean start() {
        if (!sensortagDevice.connect())
            return false;

        // TODO: Loop through all configured services for sensor tag and initialize them
        // They should be hooked up so that they contribute to a rx subject/flowable/whatever
        // that can be watched by the #logs() method.

        return true;
    }

    @Override
    protected boolean stop() {
        return sensortagDevice.disconnect();
    }

    @Override
    protected Flowable<SensorLog> logs() {
        return null;
    }
}
