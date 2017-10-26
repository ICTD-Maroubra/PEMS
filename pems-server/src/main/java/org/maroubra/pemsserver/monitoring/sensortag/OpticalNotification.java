package org.maroubra.pemsserver.monitoring.sensortag;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

/**
 * A notification from the Sensortag humidity characteristic
 * @see <a href="http://processors.wiki.ti.com/index.php/CC2650_SensorTag_User%27s_Guide#Optical_Sensor">Optical sensor spec</a>
 */
public class OpticalNotification implements BluetoothNotification<byte[]> {

    // Id's stored in created sensor log's attribute value maps
    public static final String LIGHT_INTENSITY_VALUE_ID = "light_intensity";

    // Configuration for the sensortag that is subscribed to this notification
    private final SensorConfig config;

    // Sensorlog processor to publish events too
    private final FlowableProcessor<SensorLog> processor;

    public OpticalNotification(SensorConfig config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        float lux = decodeLux(bytes[1], bytes[0]);

        SensorLog sensorLog = new SensorLog(config.getId(), ImmutableMap.of(LIGHT_INTENSITY_VALUE_ID, lux));
        processor.onNext(sensorLog);
    }

    /**
     * Decode the light intensity from bytes sent by the Sensortag
     * @param msb most significant byte
     * @param lsb least significant byte
     * @return decoded light intensity in lux (as floating point)
     */
    private float decodeLux(byte msb, byte lsb) {
        float exponent = (msb & 0xf0) >> 4;
        float mantissa = (msb & 0x0f) << 8 | (lsb & 0xff);

        return mantissa * (float)Math.pow(2, exponent) / 100.0f;
    }
}
