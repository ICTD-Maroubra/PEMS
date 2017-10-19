package org.maroubra.pemsserver.monitoring.nordic;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

public class ColorNotification implements BluetoothNotification<byte[]> {

    public static final String COLOUR_RED_VALUE_ID = "colour_red";
    public static final String COLOUR_GREEN_VALUE_ID = "colour_green";
    public static final String COLOUR_BLUE_VALUE_ID = "colour_blue";
    public static final String COLOUR_CLEAR_VALUE_ID = "colour_clear";


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

        SensorLog sensorLog = new SensorLog(config.id(), ImmutableMap.of(COLOUR_RED_VALUE_ID, red, COLOUR_GREEN_VALUE_ID, green, COLOUR_BLUE_VALUE_ID, blue, COLOUR_CLEAR_VALUE_ID, clear));
        processor.onNext(sensorLog);
    }

    private int decodeColor(byte msb, byte lsb) {
        return ((msb << 8) | (lsb & 0xff));
    }
}
