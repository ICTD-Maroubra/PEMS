package org.maroubra.pemsserver.monitoring.sensortag;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import tinyb.BluetoothNotification;

/**
 * A notification from the Sensortag humidity characteristic
 * @see <a href="http://processors.wiki.ti.com/index.php/CC2650_SensorTag_User%27s_Guide#Humidity_Sensor">Humidity sensor spec</a>
 */
public class HumidityNotification implements BluetoothNotification<byte[]> {

    // Id's stored in created sensor log's attribute value maps
    public static final String HUMIDITY_VALUE_ID = "humidity";
    public static final String TEMPERATURE_VALUE_ID = "temperature";

    // Configuration for the sensortag that is subscribed to this notification
    private final SensortagSensor.Config config;

    // Sensorlog processor to publish events too
    private final FlowableProcessor<SensorLog> processor;

    public HumidityNotification(SensortagSensor.Config config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        float temperature = decodeTemperature(bytes[1], bytes[0]);
        float humidity = decodeHumidity(bytes[3], bytes[2]);

        SensorLog sensorLog = new SensorLog(config.getId(), ImmutableMap.of(HUMIDITY_VALUE_ID, humidity, TEMPERATURE_VALUE_ID, temperature));
        processor.onNext(sensorLog);
    }

    /**
     * Decode the temperature from bytes sent by the Sensortag
     * @param msb most significant byte
     * @param lsb least significant byte
     * @return decoded temperature in celsius (as floating point)
     */
    private float decodeTemperature(byte msb, byte lsb) {
        return -40 + (165 * ((msb << 8) | (lsb & 0xff)) / 65536.0f);
    }

    /**
     * Decode the humidity from bytes sent by the Sensortag
     * @param msb most significant byte
     * @param lsb least significant byte
     * @return decoded relative humidity %(as floating point)
     */
    private float decodeHumidity(byte msb, byte lsb) {
        return ((msb << 8) | (lsb & 0xff)) * 100 / 65536.0f;
    }
}
