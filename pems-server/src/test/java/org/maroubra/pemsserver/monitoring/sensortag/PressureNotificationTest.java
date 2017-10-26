package org.maroubra.pemsserver.monitoring.sensortag;

import com.github.javafaker.Faker;
import io.reactivex.processors.ReplayProcessor;
import org.junit.Test;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;

import static com.google.common.truth.Truth.assertThat;

public class PressureNotificationTest {

    @Test
    public void producesValidSensorLog() {
        Faker faker = new Faker();
        double pressure = faker.number().randomDouble(2, 0, 5000);
        int pressureNorm = (int)(pressure * 100);

        SensorConfig config = new SensorConfig("someId", "sensortag",null);
        ReplayProcessor<SensorLog> processor = ReplayProcessor.create(10);

        PressureNotification notification = new PressureNotification(config, processor);
        // Typically we would have to watch for signing of bytes here, but we're within the range so its okay.
        notification.run(new byte[] { 0, 0, 0, (byte)(pressureNorm & 0xff), (byte)(pressureNorm >> 8 & 0xff), (byte)(pressureNorm >> 16 & 0xff) });

        SensorLog createdLog = processor.blockingFirst();

        assertThat(createdLog).isNotNull();
        assertThat(createdLog.getSensorId()).matches(config.getId());
        assertThat(createdLog.getAttributeValue()).containsKey(PressureNotification.PRESSURE_VALUE_ID);
        assertThat((float)createdLog.getAttributeValue().get(PressureNotification.PRESSURE_VALUE_ID)).isWithin(0.1f).of((float)pressure);
    }
}
