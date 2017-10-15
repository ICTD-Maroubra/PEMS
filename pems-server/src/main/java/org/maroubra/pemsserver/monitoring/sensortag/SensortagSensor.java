package org.maroubra.pemsserver.monitoring.sensortag;

import io.reactivex.Flowable;
import org.maroubra.pemsserver.monitoring.AbstractSensor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tinyb.BluetoothDevice;
import tinyb.BluetoothGattCharacteristic;
import tinyb.BluetoothGattService;
import tinyb.BluetoothNotification;

import java.util.UUID;

public class SensortagSensor extends AbstractSensor {

    private static final Logger log = LoggerFactory.getLogger(SensortagSensor.class);

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

        if (!startTemperatureCharacteristic())
            return false;

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

    private boolean startTemperatureCharacteristic() {
        BluetoothGattService service = getService(SensortagUUID.UUID_TEMP_SENSOR_ENABLE);

        BluetoothGattCharacteristic tempValue = service.find(SensortagUUID.UUID_TEMP_SENSOR_DATA.toString());
        BluetoothGattCharacteristic tempConfig = service.find(SensortagUUID.UUID_TEMP_SENSOR_DATA.toString());
        BluetoothGattCharacteristic tempPeriod = service.find(SensortagUUID.UUID_TEMP_SENSOR_PERIOD.toString());

        if (tempValue == null || tempConfig == null || tempPeriod == null) {
            log.error("Could not find the correct characteristics.");
            return false;
        }

        // 1 second update period
        tempPeriod.writeValue(new byte[] { 0x64 });

        // enable the temperature sensor
        tempConfig.writeValue(new byte[] { 0x01 });

        tempValue.enableValueNotifications(new TemperatureNotification());

        return true;
    }

    private BluetoothGattService getService(UUID uuid) {
        return sensortagDevice.find(uuid.toString());
    }

    private class TemperatureNotification implements BluetoothNotification<byte[]> {

        @Override
        public void run(byte[] bytes) {
            log.info("TEMPERATURE NOTIFICATION!!!");
        }
    }
}
