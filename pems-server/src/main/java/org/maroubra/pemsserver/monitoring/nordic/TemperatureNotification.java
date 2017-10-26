package org.maroubra.pemsserver.monitoring.nordic;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

/**
 * A notification from the Thingy52 temperature characteristic
 * @see <a href="https://nordicsemiconductor.github.io/Nordic-Thingy52-FW/documentation/firmware_architecture.html#arch_env">Environment service spec</a>
 */
public class TemperatureNotification implements BluetoothNotification<byte[]> {

    // Id's stored in created sensor log's attribute value maps
    public static final String TEMP_VALUE_ID = "temperature";

    // Configuration for the Thingy52 that is subscribed to this notification
    private final SensorConfig config;

    // Sensorlog processor to publish events too
    private final FlowableProcessor<SensorLog> processor;

    TemperatureNotification(SensorConfig config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        float ambientTemp = decodeTemperature(bytes[0], bytes[1]);

        SensorLog sensorLog = new SensorLog(config.getId(), ImmutableMap.of(TEMP_VALUE_ID, ambientTemp));
        processor.onNext(sensorLog);
    }

    /**
     * Decode temperature from bytes sent by the Thingy52
     * @param msb most significant byte
     * @param lsb least significant byte
     * @return decoded temperature in celsius
     */
    private float decodeTemperature(byte msb, byte lsb) {
        return ((msb << 8) | (lsb & 0xff)) / 256f;
    }
}
