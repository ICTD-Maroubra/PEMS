package org.maroubra.pemsserver.monitoring.sensortag;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

public class TemperatureNotification implements BluetoothNotification<byte[]> {

    private final SensortagSensorConfig config;
    private final FlowableProcessor<SensorLog> processor;

    public TemperatureNotification(SensortagSensorConfig config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        float objectTemp = decodeTemperature(bytes[1], bytes[0]);
        float ambientTemp = decodeTemperature(bytes[3], bytes[2]);

        SensorLog sensorLog = new SensorLog(config.id(), ImmutableMap.of("object_temp", objectTemp, "ambient_temp", ambientTemp));
        processor.onNext(sensorLog);
    }

    private float decodeTemperature(byte msb, byte lsb) {
        return ((msb << 8) | (lsb & 0xff)) / 128f;
    }
}
