package org.maroubra.pemsserver.monitoring.nordic;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import org.maroubra.pemsserver.bluetooth.BluetoothService;
import org.maroubra.pemsserver.monitoring.Sensor;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;
import org.maroubra.pemsserver.monitoring.annotations.FactoryClass;
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
public class Thingy52Sensor implements Sensor {

    private static final Logger log = LoggerFactory.getLogger(Thingy52Sensor.class);

    private static final String CONFIG_KEY_ADDRESS = "address";

    private final SensorConfig config;
    private final BluetoothDevice thingyDevice;
    private final FlowableProcessor<SensorLog> sensorLogPublisher = PublishProcessor.create();

    @AssistedInject
    public Thingy52Sensor(@Assisted SensorConfig config, BluetoothService service) throws InterruptedException {
        this.config = config;
        this.thingyDevice = service.getDevice(this.config.getProperty(CONFIG_KEY_ADDRESS));
    }

    @Override
    public boolean start() {
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
    public boolean stop() {
        return thingyDevice.disconnect();
    }

    @Override
    public Flowable<SensorLog> logs() {
        return sensorLogPublisher.onBackpressureLatest();
    }

    @Override
    public SensorConfig getConfig() {
        return config;
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

    @FactoryClass
    public interface Factory extends Sensor.Factory<Thingy52Sensor> {
        @Override
        Thingy52Sensor create(@Assisted SensorConfig config);
    }
}
