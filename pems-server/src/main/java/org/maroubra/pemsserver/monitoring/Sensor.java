package org.maroubra.pemsserver.monitoring;

import com.google.inject.assistedinject.Assisted;
import io.reactivex.Flowable;

/**
 * Base sensor class in PEMS. Sensors implementing this
 * could be Bluetooth IoT devices, remote HTTP API's, or any other
 * kind of device/interface.
 */
public interface Sensor {

    /**
     * Start the sensor (and start producing logs)
     * @return sensor started successfully
     */
    boolean start();

    /**
     * Stop the sensor
     * @return sensor stopped successfully
     */
    boolean stop();

    /**
     * Get a flowable of logs produced by the sensor
     * @return flowable of sensor logs
     */
    Flowable<SensorLog> logs();

    SensorConfig getConfig();

    interface Factory<T extends Sensor> {
        T create(@Assisted SensorConfig sensorConfig);
    }
}
