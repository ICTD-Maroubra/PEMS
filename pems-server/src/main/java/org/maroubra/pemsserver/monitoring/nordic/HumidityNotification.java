package org.maroubra.pemsserver.monitoring.nordic;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

/**
 * A notification from the Thingy52 humidity characteristic
 * @see <a href="https://nordicsemiconductor.github.io/Nordic-Thingy52-FW/documentation/firmware_architecture.html#arch_env">Environment service spec</a>
 */
public class HumidityNotification implements BluetoothNotification<byte[]> {

    // Id's stored in created sensor log's attribute value maps
    public static final String HUMIDITY_VALUE_ID = "relative_humidity";

    // Configuration for the Thingy52 that is subscribed to this notification
    private final SensorConfig config;

    // Sensorlog processor to publish events too
    private final FlowableProcessor<SensorLog> processor;

    public HumidityNotification(SensorConfig config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        int relativeHumidity = decodeHumidity(bytes[0]);

        SensorLog sensorLog = new SensorLog(config.getId(), ImmutableMap.of("relative_humidity", relativeHumidity));
        processor.onNext(sensorLog);
    }

    /**
     * Decode relative humidity from bytes sent by the Thingy52
     * @param data byte representing humidity
     * @return decoded relative humidity %
     */
    private int decodeHumidity(byte data) {
        return data & 0xff;
    }
}
