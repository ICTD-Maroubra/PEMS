package org.maroubra.pemsserver.monitoring.sensortag;

import com.github.javafaker.Faker;
import io.reactivex.processors.ReplayProcessor;
import org.junit.Test;
import org.maroubra.pemsserver.monitoring.SensorLog;

import static com.google.common.truth.Truth.assertThat;

public class TemperatureNotificationTest {

    @Test
    public void producesValidSensorLog() {
        Faker faker = new Faker();
        int objectTemp = faker.number().numberBetween(-50, 50);
        float objectTempDecimal = (float)faker.number().randomDouble(4, 0, 1);
        int ambientTemp = faker.number().numberBetween(-50, 50);
        float ambientTempDecimal = (float)faker.number().randomDouble(4, 0, 1);

        int objectTempNorm = ((objectTemp) << 7) | (int)(objectTempDecimal * 128);
        int ambientTempNorm = ((ambientTemp) << 7) | (int)(ambientTempDecimal * 128);

        SensortagSensor.Config config = new SensortagSensor.Config();
        config.setId("some-id");
        ReplayProcessor<SensorLog> processor = ReplayProcessor.create(10);

        TemperatureNotification notification = new TemperatureNotification(config, processor);
        notification.run(new byte[] { (byte)(objectTempNorm & 0xff), (byte)(objectTempNorm >> 8), (byte)(ambientTempNorm & 0xff), (byte)(ambientTempNorm >> 8) });

        SensorLog createdLog = processor.blockingFirst();

        assertThat(createdLog).isNotNull();
        assertThat(createdLog.getSensorId()).matches(config.getId());
        assertThat((float)createdLog.getAttributeValue().get(TemperatureNotification.OBJECT_TEMP_VALUE_ID)).isWithin(0.1f).of(objectTemp + objectTempDecimal);
        assertThat((float)createdLog.getAttributeValue().get(TemperatureNotification.AMBIENT_TEMP_VALUE_ID)).isWithin(0.1f).of(ambientTemp + ambientTempDecimal);
    }
}
