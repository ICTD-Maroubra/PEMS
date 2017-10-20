package org.maroubra.pemsserver.monitoring;

import io.reactivex.Flowable;

/**
 * Base sensor class in PEMS. Sensors implementing this
 * could be Bluetooth IoT devices, remote HTTP API's, or any other
 * kind of device/interface.
 */
public abstract class AbstractSensor {

    /**
     * Start the sensor (and start producing logs)
     * @return sensor started successfully
     */
    protected abstract boolean start();

    /**
     * Stop the sensor
     * @return sensor stopped successfully
     */
    protected abstract boolean stop();

    /**
     * Get a flowable of logs produced by the sensor
     * @return flowable of sensor logs
     */
    protected abstract Flowable<SensorLog> logs();
}
