package org.maroubra.pemsserver.monitoring.nordic;

import com.github.javafaker.Faker;
import io.reactivex.processors.ReplayProcessor;
import org.junit.Test;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;

import static com.google.common.truth.Truth.assertThat;

public class TemperatureNotificationTest {

    @Test
    public void producesValidSensorLog() {
        Faker faker = new Faker();
        int temp = faker.number().numberBetween(-50, 50);
        float decimal = (float)faker.number().randomDouble(5, 0, 1);

        SensorConfig config = new SensorConfig("someId", "nordic", null);
        ReplayProcessor<SensorLog> processor = ReplayProcessor.create(10);

        TemperatureNotification notification = new TemperatureNotification(config, processor);
        notification.run(new byte[] { (byte)temp, (byte)(decimal * 256) });

        SensorLog createdLog = processor.blockingFirst();

        assertThat(createdLog).isNotNull();
        assertThat(createdLog.getSensorId()).matches(config.getId());
        assertThat(createdLog.getAttributeValue()).containsKey(TemperatureNotification.TEMP_VALUE_ID);
        assertThat((float)createdLog.getAttributeValue().get(TemperatureNotification.TEMP_VALUE_ID)).isWithin(0.01f).of(temp + decimal);
    }
}
