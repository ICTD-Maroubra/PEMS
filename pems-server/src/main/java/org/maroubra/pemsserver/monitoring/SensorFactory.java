package org.maroubra.pemsserver.monitoring;

import org.maroubra.pemsserver.bluetooth.BluetoothService;
import org.maroubra.pemsserver.monitoring.sensortag.SensortagSensor;
import org.maroubra.pemsserver.monitoring.sensortag.SensortagSensorConfig;
import org.maroubra.pemsserver.monitoring.utsapi.UtsWebApi;
import org.maroubra.pemsserver.monitoring.utsapi.WebSensor;
import org.maroubra.pemsserver.monitoring.utsapi.WebSensorConfig;
import tinyb.BluetoothDevice;

public class SensorFactory {

    private final BluetoothService bluetoothService;
    private final UtsWebApi webApi;

    public SensorFactory(BluetoothService bluetoothService, UtsWebApi webApi) {
        this.bluetoothService = bluetoothService;
        this.webApi = webApi;
    }

    public AbstractSensor build(SensorConfig config) {
        if (config.getClass() == SensortagSensorConfig.class) {
            return buildSensortagSensor((SensortagSensorConfig) config);
        }

        else if (config.getClass() == WebSensorConfig.class) {
            return buildWebSensor((WebSensorConfig) config);
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

    private AbstractSensor buildWebSensor(WebSensorConfig sensorConfig) {
            return new WebSensor(sensorConfig, webApi);
    }
}
