package org.maroubra.pemsserver.monitoring.nordic;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

/**
 * A notification from the Thingy52 air quality characteristic
 * @see <a href="https://nordicsemiconductor.github.io/Nordic-Thingy52-FW/documentation/firmware_architecture.html#arch_env">Environment service spec</a>
 */
public class AirQualityNotification implements BluetoothNotification<byte[]> {

    // Id's stored in created sensor log's attribute value maps
    public static final String CO2_VALUE_ID = "eCO2";
    public static final String TVOC_VALUE_ID = "TVOC";

    // Configuration for the Thingy52 that is subscribed to this notification
    private final SensorConfig config;

    // Sensorlog processor to publish events too
    private final FlowableProcessor<SensorLog> processor;

    AirQualityNotification(SensorConfig config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        int eCO2 = decodeECO2(bytes[1], bytes[0]);
        int tvoc = decodeTVOC(bytes[3], bytes[2]);

        SensorLog sensorLog = new SensorLog(config.getId(), ImmutableMap.of(CO2_VALUE_ID, eCO2, TVOC_VALUE_ID, tvoc));
        processor.onNext(sensorLog);
    }

    /**
     * Decode the carbon saturation from bytes sent by the Thingy52
     * @param msb most significant byte
     * @param lsb least significant byte
     * @return decoded eCO2 in ppm
     */
    private int decodeECO2(byte msb, byte lsb) {
        return (msb << 8) | (lsb & 0xff);
    }

    /**
     * Decode the TVOC from bytes sent by the Thingy52
     * @param msb most significant byte
     * @param lsb least significant byte
     * @return decoded TVOC in ppb
     */
    private int decodeTVOC(byte msb, byte lsb) {
        return (msb << 8) | (lsb & 0xff);
    }
}
