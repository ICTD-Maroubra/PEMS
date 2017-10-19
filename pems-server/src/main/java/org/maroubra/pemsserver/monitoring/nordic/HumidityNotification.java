package org.maroubra.pemsserver.monitoring.nordic;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

public class HumidityNotification implements BluetoothNotification<byte[]> {
    private final Thingy52SensorConfig config;
    private final FlowableProcessor<SensorLog> processor;

    public HumidityNotification(Thingy52SensorConfig config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        int relativeHumidity = decodeColor(bytes[0]);

        SensorLog sensorLog = new SensorLog(config.id(), ImmutableMap.of("relative_humidity", relativeHumidity));
        processor.onNext(sensorLog);
    }

    private int decodeColor(byte data) {
        return data & 0xff;
    }
}
