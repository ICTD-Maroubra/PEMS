package org.maroubra.pemsserver.monitoring.nordic;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import org.maroubra.pemsserver.monitoring.AbstractSensor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tinyb.BluetoothDevice;
import tinyb.BluetoothGattCharacteristic;
import tinyb.BluetoothGattService;
import tinyb.BluetoothNotification;

import java.util.UUID;

/**
 * Nordic Thingy52 Sensor. Enables subscription to a subset of bluetooth
 * characteristics available to the sensor:
 *  - Temperature
 *  - Humidity
 *  - Pressure
 *  - Color
 *  - Air Quality
 */
public class Thingy52Sensor extends AbstractSensor {

    private static final Logger log = LoggerFactory.getLogger(Thingy52Sensor.class);

    private final Thingy52SensorConfig config;
    private final BluetoothDevice thingyDevice;
    private final FlowableProcessor<SensorLog> sensorLogPublisher = PublishProcessor.create();

    public Thingy52Sensor(Thingy52SensorConfig config, BluetoothDevice thingyDevice) {
        this.config = config;
        this.thingyDevice = thingyDevice;
    }
    @Override
    protected boolean start() {
        if (!thingyDevice.connect())
            return false;

        BluetoothGattService weatherService = getService(Thingy52UUID.UUID_WEATHER_SERVICE);

        boolean allCharacteristicsStarted =
                startCharacteristic(weatherService, Thingy52UUID.UUID_TEMP_SENSOR_DATA.toString(), new TemperatureNotification(config, sensorLogPublisher)) &&
                startCharacteristic(weatherService, Thingy52UUID.UUID_COLOR_SENSOR_DATA.toString(), new ColorNotification(config, sensorLogPublisher)) &&
                startCharacteristic(weatherService, Thingy52UUID.UUID_GAS_SENSOR_DATA.toString(), new AirQualityNotification(config, sensorLogPublisher)) &&
                startCharacteristic(weatherService, Thingy52UUID.UUID_PRES_SENSOR_DATA.toString(), new PressureNotification(config, sensorLogPublisher)) &&
                startCharacteristic(weatherService, Thingy52UUID.UUID_HUM_SENSOR_DATA.toString(), new HumidityNotification(config, sensorLogPublisher));

        if (!allCharacteristicsStarted) {
            thingyDevice.disconnect();
            return false;
        }
        
        return true;
    }

    @Override
    protected boolean stop() {
        return thingyDevice.disconnect();
    }

    @Override
    protected Flowable<SensorLog> logs() {
        return sensorLogPublisher.onBackpressureLatest();
    }

    /**
     * Start and subscribe to a bluetooth GATT characteristic on a Thingy52 service
     * @param gattService Bluetooth GATT service containing characteristic
     * @param characteristicUuid UUID of characteristic to subscribe too
     * @param notificationCallback Notification callback to run on event
     * @return successfully subscribed to bluetooth characteristic
     */
    private boolean startCharacteristic(BluetoothGattService gattService, String characteristicUuid, BluetoothNotification<byte[]> notificationCallback) {
        BluetoothGattCharacteristic value = gattService.find(characteristicUuid);

        if (value == null) {
            log.error("Could not find the characteristic for UUID {}.", characteristicUuid);
            return false;
        }

        value.enableValueNotifications(notificationCallback);

        return true;
    }

    /**
     * Gets a Thingy52 bluetooth GATT service
     * @return Bluetooth service
     */
    private BluetoothGattService getService(UUID uuid) {
        return thingyDevice.find(uuid.toString());
    }
}
