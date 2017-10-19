package org.maroubra.pemsserver.monitoring.nordic;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

public class AirQualityNotification implements BluetoothNotification<byte[]> {

    private final Thingy52SensorConfig config;
    private final FlowableProcessor<SensorLog> processor;

    AirQualityNotification(Thingy52SensorConfig config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        int eCO2 = decodePPM(bytes[1], bytes[0]);
        int tvoc = decodeTVOC(bytes[3], bytes[2]);

        SensorLog sensorLog = new SensorLog(config.id(), ImmutableMap.of("eCO2", eCO2, "TVOC", tvoc));
        processor.onNext(sensorLog);
    }

    private int decodePPM(byte msb, byte lsb) {
        return (msb << 8) | (lsb & 0xff);
    }

    private int decodeTVOC(byte msb, byte lsb) {
        return (msb << 8) | (lsb & 0xff);
    }
}
