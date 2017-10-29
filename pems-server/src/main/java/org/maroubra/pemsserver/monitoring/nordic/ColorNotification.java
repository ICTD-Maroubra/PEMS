package org.maroubra.pemsserver.monitoring.nordic;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

/**
 * A notification from the Thingy52 color (light) characteristic
 * @see <a href="https://nordicsemiconductor.github.io/Nordic-Thingy52-FW/documentation/firmware_architecture.html#arch_env">Environment service spec</a>
 */
public class ColorNotification implements BluetoothNotification<byte[]> {

    // Id's stored in created sensor log's attribute value maps
    public static final String COLOUR_RED_VALUE_ID = "colour_red";
    public static final String COLOUR_GREEN_VALUE_ID = "colour_green";
    public static final String COLOUR_BLUE_VALUE_ID = "colour_blue";
    public static final String COLOUR_CLEAR_VALUE_ID = "colour_clear";

    // Configuration for the sensortag that is subscribed to this notification
    private final SensorConfig config;

    // Sensorlog processor to publish events too
    private final FlowableProcessor<SensorLog> processor;

    ColorNotification(SensorConfig config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        int red = decodeColor(bytes[1], bytes[0]);
        int green = decodeColor(bytes[3], bytes[2]);
        int blue = decodeColor(bytes[5], bytes[4]);
        int clear = decodeColor(bytes[7], bytes[6]);

        SensorLog sensorLog = new SensorLog(config.getId(), ImmutableMap.of(COLOUR_RED_VALUE_ID, red, COLOUR_GREEN_VALUE_ID, green, COLOUR_BLUE_VALUE_ID, blue, COLOUR_CLEAR_VALUE_ID, clear));
        processor.onNext(sensorLog);
    }

    /**
     * Decode a color from bytes sent by the Thingy52
     * @param msb most significant byte
     * @param lsb least significant byte
     * @return decoded color
     */
    private int decodeColor(byte msb, byte lsb) {
        return ((msb << 8) | (lsb & 0xff));
    }
}
