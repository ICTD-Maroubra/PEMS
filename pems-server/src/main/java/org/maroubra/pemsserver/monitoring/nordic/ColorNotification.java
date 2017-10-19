package org.maroubra.pemsserver.monitoring.nordic;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

public class ColorNotification implements BluetoothNotification<byte[]> {

    private final Thingy52SensorConfig config;
    private final FlowableProcessor<SensorLog> processor;

    ColorNotification(Thingy52SensorConfig config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        int red = decodeColor(bytes[1], bytes[0]);
        int green = decodeColor(bytes[3], bytes[2]);
        int blue = decodeColor(bytes[5], bytes[4]);
        int clear = decodeColor(bytes[7], bytes[6]);

        SensorLog sensorLog = new SensorLog(config.id(), ImmutableMap.of("red", red, "green", green, "blue", blue, "clear", clear));
        processor.onNext(sensorLog);
    }

    private int decodeColor(byte msb, byte lsb) {
        return ((msb << 8) | (lsb & 0xff));
    }
}
