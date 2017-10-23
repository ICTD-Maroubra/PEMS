package org.maroubra.pemsserver.monitoring.sensortag;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;
import org.maroubra.pemsserver.bluetooth.BluetoothService;
import org.maroubra.pemsserver.monitoring.Sensor;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;
import org.maroubra.pemsserver.monitoring.annotations.ConfigClass;
import org.maroubra.pemsserver.monitoring.annotations.FactoryClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tinyb.BluetoothDevice;
import tinyb.BluetoothGattCharacteristic;
import tinyb.BluetoothGattService;

import java.util.UUID;

/**
 * Texas Instruments Sensortag CC2650 Sensor. Enables subscription to a subset of bluetooth
 * characteristics available to the sensor:
 *  - Temperature
 *  - Humidity
 *  - Barometer
 *  - Optical
 */
public class SensortagSensor implements Sensor {

    private static final Logger log = LoggerFactory.getLogger(SensortagSensor.class);

    private final Config config;
    private final BluetoothDevice sensortagDevice;
    private final PublishProcessor<SensorLog> sensorLogPublisher = PublishProcessor.create();

    @AssistedInject
    public SensortagSensor(@Assisted SensorConfig config, BluetoothService bluetoothService) throws InterruptedException {
        this.config = (Config) config;
        this.sensortagDevice = bluetoothService.getDevice(this.config.address);
    }

    @Override
    public boolean start() {
        if (!sensortagDevice.connect())
            return false;

        boolean allCharacteristicsStarted =
                startTemperatureCharacteristic() &&
                startHumidityCharacteristic() &&
                startBarometerCharacteristic() &&
                startOpticalCharacteristic();

        if (!allCharacteristicsStarted) {
            sensortagDevice.disconnect();
            return false;
        }

        return true;
    }

    @Override
    public boolean stop() {
        return sensortagDevice.disconnect();
    }

    @Override
    public Flowable<SensorLog> logs() {
        return sensorLogPublisher.onBackpressureLatest();
    }

    /**
     * Gets the temperature characteristic on the Sensortag, configures, enables, and subscribes
     * to notifications from it.
     * @return successfully subscribed to temperature characteristic
     */
    private boolean startTemperatureCharacteristic() {
        BluetoothGattService service = getService(SensortagUUID.UUID_TEMP_SENSOR_ENABLE);

        BluetoothGattCharacteristic tempValue = service.find(SensortagUUID.UUID_TEMP_SENSOR_DATA.toString());
        BluetoothGattCharacteristic tempConfig = service.find(SensortagUUID.UUID_TEMP_SENSOR_CONFIG.toString());
        BluetoothGattCharacteristic tempPeriod = service.find(SensortagUUID.UUID_TEMP_SENSOR_PERIOD.toString());

        if (tempValue == null || tempConfig == null || tempPeriod == null) {
            log.error("Could not find the correct characteristics.");
            return false;
        }

        // 1 second update period
        tempPeriod.writeValue(new byte[] { 0x64 });

        // Enable the temperature sensor
        tempConfig.writeValue(new byte[] { 0x01 });

        tempValue.enableValueNotifications(new TemperatureNotification(config, sensorLogPublisher));

        return true;
    }

    /**
     * Gets the humidity characteristic on the Sensortag, configures, enables, and subscribes
     * to notifications from it.
     * @return successfully subscribed to humidity characteristic
     */
    private boolean startHumidityCharacteristic() {
        BluetoothGattService service = getService(SensortagUUID.UUID_HUM_SENSOR_ENABLE);

        BluetoothGattCharacteristic humidityValue = service.find(SensortagUUID.UUID_HUM_SENSOR_DATA.toString());
        BluetoothGattCharacteristic humidityConfig = service.find(SensortagUUID.UUID_HUM_SENSOR_CONFIG.toString());
        BluetoothGattCharacteristic humidityPeriod = service.find(SensortagUUID.UUID_HUM_SENSOR_PERIOD.toString());

        if (humidityValue == null || humidityConfig == null || humidityPeriod == null) {
            log.error("Could not find the correct characteristics.");
            return false;
        }

        // 1 second update period
        humidityPeriod.writeValue(new byte[] { 0x64 });

        // Enable the humidity sensor
        humidityConfig.writeValue(new byte[] { 0x01 });

        humidityValue.enableValueNotifications(new HumidityNotification(config, sensorLogPublisher));

        return true;
    }

    /**
     * Gets the barometer (pressure) characteristic on the Sensortag, configures, enables, and subscribes
     * to notifications from it.
     * @return successfully subscribed to temperature characteristic
     */
    private boolean startBarometerCharacteristic() {
        BluetoothGattService service = getService(SensortagUUID.UUID_BARO_SENSOR_ENABLE);

        BluetoothGattCharacteristic barometerValue = service.find(SensortagUUID.UUID_BARO_SENSOR_DATA.toString());
        BluetoothGattCharacteristic barometerConfig = service.find(SensortagUUID.UUID_BARO_SENSOR_CONFIG.toString());
        BluetoothGattCharacteristic barometerPeriod = service.find(SensortagUUID.UUID_BARO_SENSOR_PERIOD.toString());

        if (barometerValue == null || barometerConfig == null || barometerPeriod == null) {
            log.error("Could not find the correct characteristics.");
            return false;
        }

        // 1 second update period
        barometerPeriod.writeValue(new byte[] { 0x64 });

        // Enable the barometer sensor
        barometerConfig.writeValue(new byte[] { 0x01 });

        barometerValue.enableValueNotifications(new PressureNotification(config, sensorLogPublisher));

        return true;
    }

    /**
     * Gets the optical (light) characteristic on the Sensortag, configures, enables, and subscribes
     * to notifications from it.
     * @return successfully subscribed to temperature characteristic
     */
    private boolean startOpticalCharacteristic() {
        BluetoothGattService service = getService(SensortagUUID.UUID_LUXO_SENSOR_ENABLE);

        BluetoothGattCharacteristic opticalValue = service.find(SensortagUUID.UUID_LUXO_SENSOR_DATA.toString());
        BluetoothGattCharacteristic opticalConfig = service.find(SensortagUUID.UUID_LUXO_SENSOR_CONFIG.toString());
        BluetoothGattCharacteristic opticalPeriod = service.find(SensortagUUID.UUID_LUXO_SENSOR_PERIOD.toString());

        if (opticalValue == null || opticalConfig == null || opticalPeriod == null) {
            log.error("Could not find the correct characteristics.");
            return false;
        }

        // 1 second update period
        opticalPeriod.writeValue(new byte[] { 0x64 });

        // Enable the optical sensor
        opticalConfig.writeValue(new byte[] { 0x01 });

        opticalValue.enableValueNotifications(new OpticalNotification(config, sensorLogPublisher));

        return true;
    }

    /**
     * Gets the base Sensortag bluetooth GATT service
     * @return Bluetooth service
     */
    private BluetoothGattService getService(UUID uuid) {
        return sensortagDevice.find(uuid.toString());
    }

    @FactoryClass
    public interface Factory extends Sensor.Factory<SensortagSensor> {
        @Override
        SensortagSensor create(@Assisted SensorConfig config);

        @Override
        Config getConfig();
    }

    @ConfigClass
    public static class Config implements SensorConfig {
        private String id;
        private String address;

        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String type() {
            return SensortagSensor.class.getCanonicalName();
        }

        /**
         * MAC address of sensortag
         * @return MAC address
         */
        public String getAddress() {
            return address;
        }

        /**
         * Set the MAC address
         * @param address MAC address
         */
        public void setAddress(String address) {
            this.address = address;
        }
    }
}
