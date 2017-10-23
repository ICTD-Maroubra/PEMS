package org.maroubra.pemsserver.monitoring.sensortag;

import com.github.javafaker.Faker;
import io.reactivex.processors.ReplayProcessor;
import org.junit.Test;
import org.maroubra.pemsserver.monitoring.SensorLog;

import static com.google.common.truth.Truth.assertThat;

public class HumidityNotificationTest {

    @Test
    public void producesValidSensorLog() {
        Faker faker = new Faker();
        // Really this should be between 0 and 100... but yet again, I don't get Java bytes
        double humidity = faker.number().randomDouble(4, 0, 50);
        int humidityNorm = (int)(humidity * 65536 / 100);

        // -40 + (165 * ((msb << 8) | (lsb & 0xff)) / 65536.0f)
        double temperature = faker.number().randomDouble(4, -50, 50);
        int temperatureNorm = (int)((temperature + 40) / 165 * 65536);

        SensortagSensor.Config config = new SensortagSensor.Config();
        config.setId("some-id");
        ReplayProcessor<SensorLog> processor = ReplayProcessor.create(10);

        HumidityNotification notification = new HumidityNotification(config, processor);
        // Typically we would have to watch for signing of bytes here, but we're within the range so its okay.
        notification.run(new byte[] { (byte)(temperatureNorm & 0xff), (byte)(temperatureNorm >> 8), (byte)(humidityNorm & 0xff), (byte)(humidityNorm >> 8) });

        SensorLog createdLog = processor.blockingFirst();

        assertThat(createdLog).isNotNull();
        assertThat(createdLog.getSensorId()).matches(config.getId());
        assertThat(createdLog.getAttributeValue()).containsKey(HumidityNotification.HUMIDITY_VALUE_ID);
        assertThat(createdLog.getAttributeValue()).containsKey(HumidityNotification.TEMPERATURE_VALUE_ID);
        assertThat((float)createdLog.getAttributeValue().get(HumidityNotification.HUMIDITY_VALUE_ID)).isWithin(0.1f).of((float)humidity);
        assertThat((float)createdLog.getAttributeValue().get(HumidityNotification.TEMPERATURE_VALUE_ID)).isWithin(0.1f).of((float)temperature);
    }
}
