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

import java.util.UUID;

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

    private boolean startTemperatureCharacteristic() {
        BluetoothGattService service = getService(Thingy52UUID.UUID_TEMP_SENSOR_ENABLE);

        BluetoothGattCharacteristic tempValue = service.find(Thingy52UUID.UUID_TEMP_SENSOR_DATA.toString());

        if (tempValue == null) {
            log.error("Could not find the correct characteristics.");
            return false;
        }

        tempValue.enableValueNotifications(new TemperatureNotification(config, sensorLogPublisher));


        return true;
    }

    private BluetoothGattService getService(UUID uuid) {
        return thingyDevice.find(uuid.toString());
    }
}
