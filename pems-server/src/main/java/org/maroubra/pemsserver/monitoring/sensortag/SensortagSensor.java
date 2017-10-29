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
 *  - Acceleration
 */
public class SensortagSensor implements Sensor {

    private static final Logger log = LoggerFactory.getLogger(SensortagSensor.class);

    private final Config config;
    private final BluetoothDevice sensortagDevice;
    private final PublishProcessor<SensorLog> sensorLogPublisher = PublishProcessor.create();

    private boolean thermometerSensorEnabled = false;
    private boolean hygrometerSensorEnabled = false;
    private boolean barometerSensorEnabled = false;
    private boolean luxometerSensorEnabled = false;
    private boolean accelerometerSensorEnabled = false;

    public static final String THERMOMETER = "Thermometer";
    public static final String HYGROMETER = "Hygrometer";
    public static final String BAROMETER = "Barometer";
    public static final String LUXOMETER = "Luxometer";
    public static final String ACCELEROMETER = "Accelerometer";

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
                startOpticalCharacteristic() &&
                startAccelerometerCharacteristic();

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
     * Gets the specified characteristic on the Sensortag and disables it.
     * @param sensor - sensor specified to be disabled
     * @return successfully stopped the specified characteristic.
     */
    public boolean stopSelectedSensor(String sensor) {
        switch(sensor) {
            case THERMOMETER:
                stopTemperatureCharacteristic();
                break;
            case BAROMETER:
                stopBarometerCharacteristic();
                break;
            case HYGROMETER:
                stopHumidityCharacteristic();
                break;
            case LUXOMETER:
                stopOpticalCharacteristic();
                break;
            case ACCELEROMETER:
                stopAccelerationCharacteristic();
                break;
            default:
                log.info("Sensor does not exist!, please input a valid sensor.");
                return false;
        }
        return true;
    }


    /**
     * Gets the specified characteristic on the Sensortag and configures the period.
     * @param sensor - sensor specified to be configured
     * @param period - the specified interval in which it updates in milliseconds. (300 ms (0x1E) is lowest interval that is stable)
     * @return successfully updated the period
     */
    public boolean alterSensorUpdateInterval(String sensor, byte[] period) {
        BluetoothGattService service;
        switch (sensor) {
            case THERMOMETER:
                service = getService(SensortagUUID.UUID_TEMP_SENSOR_ENABLE);
                BluetoothGattCharacteristic temperaturePeriod = service.find(SensortagUUID.UUID_TEMP_SENSOR_PERIOD.toString());
                temperaturePeriod.writeValue(period);
                break;
            case BAROMETER:
                service = getService(SensortagUUID.UUID_BARO_SENSOR_ENABLE);
                BluetoothGattCharacteristic baroPeriod = service.find(SensortagUUID.UUID_BARO_SENSOR_PERIOD.toString());
                baroPeriod.writeValue(period);
                break;
            case HYGROMETER:
                service = getService(SensortagUUID.UUID_HUM_SENSOR_ENABLE);
                BluetoothGattCharacteristic humidityPeriod = service.find(SensortagUUID.UUID_HUM_SENSOR_PERIOD.toString());
                humidityPeriod.writeValue(period);
                break;
            case LUXOMETER:
                service = getService(SensortagUUID.UUID_LUXO_SENSOR_ENABLE);
                BluetoothGattCharacteristic luxoPeriod = service.find(SensortagUUID.UUID_LUXO_SENSOR_PERIOD.toString());
                luxoPeriod.writeValue(period);
                break;
            case ACCELEROMETER:
                service = getService(SensortagUUID.UUID_ACC_SENSOR_ENABLE);
                BluetoothGattCharacteristic accelPeriod = service.find(SensortagUUID.UUID_ACC_SENSOR_PERIOD.toString());
                accelPeriod.writeValue(period);
                break;
            default:
                log.info("Sensor does not exist!, please input a valid sensor.");
                return false;
        }
        return true;
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
        thermometerSensorEnabled = true;

        return true;
    }

    /**
     * Gets the temperature characteristic on the Sensortag and disables it - stopping all notifications from it.
     * @return successfully disabled temperature characteristic
     */
    private boolean stopTemperatureCharacteristic() {
        BluetoothGattService service = getService(SensortagUUID.UUID_TEMP_SENSOR_ENABLE);

        BluetoothGattCharacteristic tempConfig = service.find(SensortagUUID.UUID_TEMP_SENSOR_CONFIG.toString());

        if (tempConfig == null) {
            log.error("Could not find the correct characteristic.");
            return false;
        }
        // disable the temperature sensor
        tempConfig.writeValue(new byte[] { 0x00 });
        thermometerSensorEnabled = false;

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
        hygrometerSensorEnabled = true;

        return true;
    }

    /**
     * Gets the humidity characteristic on the Sensortag and disables it - stopping all notifications from it.
     * @return successfully disabled humidity characteristic
     */
    private boolean stopHumidityCharacteristic() {
        BluetoothGattService service = getService(SensortagUUID.UUID_HUM_SENSOR_ENABLE);

        BluetoothGattCharacteristic humidityConfig = service.find(SensortagUUID.UUID_HUM_SENSOR_CONFIG.toString());

        if (humidityConfig == null) {
            log.error("Could not find the correct characteristic.");
            return false;
        }
        // disable the humidity sensor
        humidityConfig.writeValue(new byte[] { 0x00 });
        hygrometerSensorEnabled = false;

        return true;
    }

    /**
     * Gets the barometer (pressure) characteristic on the Sensortag, configures, enables, and subscribes
     * to notifications from it.
     * @return successfully subscribed to barometer (pressure) characteristic
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
        barometerSensorEnabled = true;

        return true;
    }


    /**
     * Gets the barometer characteristic on the Sensortag and disables it - stopping all notifications from it.
     * @return successfully disabled barometer characteristic
     */
    private boolean stopBarometerCharacteristic() {
        BluetoothGattService service = getService(SensortagUUID.UUID_BARO_SENSOR_ENABLE);

        BluetoothGattCharacteristic barometerConfig = service.find(SensortagUUID.UUID_BARO_SENSOR_CONFIG.toString());

        if (barometerConfig == null) {
            log.error("Could not find the correct characteristic.");
            return false;
        }
        // disable the barometer sensor
        barometerConfig.writeValue(new byte[] { 0x00 });
        barometerSensorEnabled = false;
        return true;
    }

    /**
     * Gets the optical (light) characteristic on the Sensortag, configures, enables, and subscribes
     * to notifications from it.
     * @return successfully subscribed to optical (light) characteristic
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
        luxometerSensorEnabled = true;

        return true;
    }

    /**
     * Gets the optical (light) characteristic on the Sensortag and disables it - stopping all notifications from it.
     * @return successfully disabled optical (light) characteristic
     */
    private boolean stopOpticalCharacteristic() {
        BluetoothGattService service = getService(SensortagUUID.UUID_LUXO_SENSOR_ENABLE);

        BluetoothGattCharacteristic opticalConfig = service.find(SensortagUUID.UUID_LUXO_SENSOR_CONFIG.toString());

        if (opticalConfig == null) {
            log.error("Could not find the correct characteristic.");
            return false;
        }
        // disable the optical sensor
        opticalConfig.writeValue(new byte[] { 0x00 });
        luxometerSensorEnabled = false;

        return true;
    }

    /**
     * Gets the acceleration characteristic on the Sensortag, configures, enables, and subscribes
     * to notifications from it.
     * @return successfully subscribed to acceleration characteristic
     */
    private boolean startAccelerometerCharacteristic() {
        BluetoothGattService service = getService(SensortagUUID.UUID_ACC_SENSOR_ENABLE);

        BluetoothGattCharacteristic accelValue = service.find(SensortagUUID.UUID_ACC_SENSOR_DATA.toString());
        BluetoothGattCharacteristic accelConfig = service.find(SensortagUUID.UUID_ACC_SENSOR_CONFIG.toString());
        BluetoothGattCharacteristic accelPeriod = service.find(SensortagUUID.UUID_ACC_SENSOR_PERIOD.toString());

        if (accelValue == null || accelConfig == null || accelPeriod == null) {
            log.error("Could not find the correct characteristics.");
            return false;
        }

        // 1 second update period
        accelPeriod.writeValue(new byte[] { 0x64 });

        // enable the accelerometer sensor
        accelConfig.writeValue(new byte[] { (byte) 0x7F , (byte) 0x00});

        accelValue.enableValueNotifications(new AccelerationNotification(config, sensorLogPublisher));
        accelerometerSensorEnabled = true;

        return true;
    }

    /**
     * Gets the acceleration characteristic on the Sensortag and disables it - stopping all notifications from it.
     * @return successfully disabled acceleration characteristic
     */
    private boolean stopAccelerationCharacteristic() {
        BluetoothGattService service = getService(SensortagUUID.UUID_ACC_SENSOR_ENABLE);

        BluetoothGattCharacteristic accelerationConfig = service.find(SensortagUUID.UUID_ACC_SENSOR_CONFIG.toString());

        if (accelerationConfig == null) {
            log.error("Could not find the correct characteristic.");
            return false;
        }
        // disable the accelerometer sensor
        accelerationConfig.writeValue(new byte[] { 0x00 });
        accelerometerSensorEnabled = false;

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
