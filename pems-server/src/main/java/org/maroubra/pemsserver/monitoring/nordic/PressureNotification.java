package org.maroubra.pemsserver.monitoring.nordic;

import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Ints;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

/**
 * A notification from the Thingy52 pressure characteristic
 * @see <a href="https://nordicsemiconductor.github.io/Nordic-Thingy52-FW/documentation/firmware_architecture.html#arch_env">Environment service spec</a>
 */
public class PressureNotification implements BluetoothNotification<byte[]> {

    // Id's stored in created sensor log's attribute value maps
    public static final String PRESSURE_VALUE_ID = "pressure";

    // Configuration for the Thingy52 that is subscribed to this notification
    private final Thingy52SensorConfig config;

    // Sensorlog processor to publish events too
    private final FlowableProcessor<SensorLog> processor;

    PressureNotification(Thingy52SensorConfig config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        float pressure = decodePressure(bytes);

        SensorLog sensorLog = new SensorLog(config.id(), ImmutableMap.of(PRESSURE_VALUE_ID, pressure));
        processor.onNext(sensorLog);
    }

    /**
     * Decode pressure from bytes sent by the Thingy52
     * @param bytes bytes representing pressure
     * @return decoded pressure in HPa
     */
    private float decodePressure(byte[] bytes) {
        return Ints.fromBytes(bytes[3], bytes[2], bytes[1], bytes[0]) + ((bytes[4] & 0xff) / 256f);
    }
}
