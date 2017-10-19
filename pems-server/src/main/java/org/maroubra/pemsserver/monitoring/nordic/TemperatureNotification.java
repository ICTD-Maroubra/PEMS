package org.maroubra.pemsserver.monitoring.nordic;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

public class TemperatureNotification implements BluetoothNotification<byte[]> {

    public static final String TEMP_VALUE_ID = "temperature";

    private final Thingy52SensorConfig config;
    private final FlowableProcessor<SensorLog> processor;

    TemperatureNotification(Thingy52SensorConfig config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        float ambientTemp = decodeTemperature(bytes[0], bytes[1]);

        SensorLog sensorLog = new SensorLog(config.id(), ImmutableMap.of(TEMP_VALUE_ID, ambientTemp));
        processor.onNext(sensorLog);
    }

    private float decodeTemperature(byte msb, byte lsb) {
        return ((msb << 8) | (lsb & 0xff)) / 256f;
    }
}
