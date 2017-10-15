package org.maroubra.pemsserver.monitoring.sensortag;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.PublishProcessor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

public class HumidityNotification implements BluetoothNotification<byte[]> {

    private final SensortagSensorConfig config;
    private final PublishProcessor<SensorLog> processor;

    public HumidityNotification(SensortagSensorConfig config, PublishProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        float temperature = decodeTemperature(bytes[1], bytes[0]);
        float humidity = decodeHumidity(bytes[3], bytes[2]);

        SensorLog sensorLog = new SensorLog(config.id(), ImmutableMap.of("humidity", humidity, "temperature", temperature));
        processor.onNext(sensorLog);
    }

    private float decodeTemperature(byte msb, byte lsb) {
        return -40 + (165 * ((msb << 8) | (lsb & 0xff)) / 65536.0f);
    }

    private float decodeHumidity(byte msb, byte lsb) {
        return ((msb << 8) | (lsb & 0xff)) * 100 / 65536.0f;
    }
}
