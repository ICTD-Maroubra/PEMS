package org.maroubra.pemsserver.monitoring.nordic;

import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Ints;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

public class PressureNotification implements BluetoothNotification<byte[]> {

    public static final String PRESSURE_VALUE_ID = "pressure";

    private final Thingy52SensorConfig config;
    private final FlowableProcessor<SensorLog> processor;

    PressureNotification(Thingy52SensorConfig config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        float pressure = decodePressure(bytes);

        SensorLog sensorLog = new SensorLog(config.id(), ImmutableMap.of(PRESSURE_VALUE_ID, pressure));
        processor.onNext(sensorLog);
    }

    private float decodePressure(byte[] bytes) {
        return Ints.fromBytes(bytes[3], bytes[2], bytes[1], bytes[0]) + ((bytes[4] & 0xff) / 256f);
    }
}
