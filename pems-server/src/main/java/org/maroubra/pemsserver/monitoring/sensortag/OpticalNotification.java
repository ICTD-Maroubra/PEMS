package org.maroubra.pemsserver.monitoring.sensortag;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.PublishProcessor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

public class OpticalNotification implements BluetoothNotification<byte[]> {

    private final SensortagSensorConfig config;
    private final PublishProcessor<SensorLog> processor;

    public OpticalNotification(SensortagSensorConfig config, PublishProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        float lux = decodeLux(bytes[1], bytes[0]);

        SensorLog sensorLog = new SensorLog(config.id(), ImmutableMap.of("lux", lux));
        processor.onNext(sensorLog);
    }

    private float decodeLux(byte msb, byte lsb) {
        float exponent = (msb & 0xf0) >> 4;
        float mantissa = (msb & 0x0f) << 8 | (lsb & 0xff);

        return mantissa * (float)Math.pow(2, exponent) / 100.0f;
    }
}
