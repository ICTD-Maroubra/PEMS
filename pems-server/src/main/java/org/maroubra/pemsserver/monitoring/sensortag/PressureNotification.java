package org.maroubra.pemsserver.monitoring.sensortag;

import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Ints;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

public class PressureNotification implements BluetoothNotification<byte[]> {

    public static final String PRESSURE_VALUE_ID = "pressure";

    private final SensortagSensorConfig config;
    private final FlowableProcessor<SensorLog> processor;

    public PressureNotification(SensortagSensorConfig config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        float pressure = decodePressure(new byte[]{0x00, bytes[5], bytes[4], bytes[3]});

        SensorLog sensorLog = new SensorLog(config.id(), ImmutableMap.of("pressure", pressure));
        processor.onNext(sensorLog);
    }

    private float decodePressure(byte[] bytes) {
        return Ints.fromByteArray(bytes) / 100.0f;
    }
}
