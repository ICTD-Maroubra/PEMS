package org.maroubra.pemsserver.monitoring;

import org.maroubra.pemsserver.bluetooth.BluetoothService;
import org.maroubra.pemsserver.monitoring.sensortag.SensortagSensor;
import org.maroubra.pemsserver.monitoring.sensortag.SensortagSensorConfig;
import tinyb.BluetoothDevice;

public class SensorFactory {

    private final BluetoothService bluetoothService;

    public SensorFactory(BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }

    public AbstractSensor build(SensorConfig config) {
        if (config.getClass() == SensortagSensorConfig.class) {
            return buildSensortagSensor((SensortagSensorConfig) config);
        }

        // TODO: throw better exception than this
        throw new RuntimeException();
    }

    private SensortagSensor buildSensortagSensor(SensortagSensorConfig sensorConfig) {
        try {
            BluetoothDevice device = bluetoothService.getDevice(sensorConfig.getAddress());
            return new SensortagSensor(sensorConfig, device);
        } catch (InterruptedException ex) {
            // TODO: log and rethrow better error
            return null;
        }
    }
}
