package org.maroubra.pemsserver.monitoring.sensortag;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

/**
 * A notification from the Sensortag temperature characteristic
 * @see <a href="http://processors.wiki.ti.com/index.php/CC2650_SensorTag_User%27s_Guide#Barometric_Pressure_Sensor">Barometer sensor spec</a>
 */
public class TemperatureNotification implements BluetoothNotification<byte[]> {

    // Id's stored in created sensor log's attribute value maps
    public static final String OBJECT_TEMP_VALUE_ID = "object_temperature";
    public static final String AMBIENT_TEMP_VALUE_ID = "ambient_temperature";

    // Configuration for the sensortag that is subscribed to this notification
    private final SensortagSensor.Config config;

    // Sensorlog processor to publish events too
    private final FlowableProcessor<SensorLog> processor;

    public TemperatureNotification(SensortagSensor.Config config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        float objectTemp = decodeTemperature(bytes[1], bytes[0]);
        float ambientTemp = decodeTemperature(bytes[3], bytes[2]);

        SensorLog sensorLog = new SensorLog(config.getId(), ImmutableMap.of(OBJECT_TEMP_VALUE_ID, objectTemp, AMBIENT_TEMP_VALUE_ID, ambientTemp));
        processor.onNext(sensorLog);
    }

    /**
     * Decode the temperature from bytes sent by the Sensortag
     * @param msb most significant byte
     * @param lsb least significant byte
     * @return decoded temperature in celsius (as floating point)
     */
    private float decodeTemperature(byte msb, byte lsb) {
        return ((msb << 8) | (lsb & 0xff)) / 128f;
    }
}
