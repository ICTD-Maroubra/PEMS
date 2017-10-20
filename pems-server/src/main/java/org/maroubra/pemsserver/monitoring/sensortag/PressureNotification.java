package org.maroubra.pemsserver.monitoring.sensortag;

import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Ints;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

/**
 * A notification from the Sensortag pressure characteristic
 * @see <a href="http://processors.wiki.ti.com/index.php/CC2650_SensorTag_User%27s_Guide#Barometric_Pressure_Sensor">Barometer sensor spec</a>
 */
public class PressureNotification implements BluetoothNotification<byte[]> {

    // Id's stored in created sensor log's attribute value maps
    public static final String PRESSURE_VALUE_ID = "pressure";

    // Configuration for the sensortag that is subscribed to this notification
    private final SensortagSensorConfig config;

    // Sensorlog processor to publish events too
    private final FlowableProcessor<SensorLog> processor;

    public PressureNotification(SensortagSensorConfig config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        float pressure = decodePressure(new byte[]{0x00, bytes[5], bytes[4], bytes[3]});

        SensorLog sensorLog = new SensorLog(config.id(), ImmutableMap.of("pressure", pressure));
        processor.onNext(sensorLog);
    }

    /**
     * Decode the pressure from bytes sent by the Sensortag
     * @param bytes bytes of pressure notification (expects 4 bytes)
     * @return decoded pressure (as floating point)
     */
    private float decodePressure(byte[] bytes) {
        return Ints.fromByteArray(bytes) / 100.0f;
    }
}
