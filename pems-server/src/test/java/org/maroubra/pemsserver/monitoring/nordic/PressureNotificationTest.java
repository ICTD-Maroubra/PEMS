package org.maroubra.pemsserver.monitoring.nordic;

import com.github.javafaker.Faker;
import com.google.common.primitives.Ints;
import io.reactivex.processors.ReplayProcessor;
import org.junit.Test;
import org.maroubra.pemsserver.monitoring.SensorLog;

import static com.google.common.truth.Truth.assertThat;

public class PressureNotificationTest {

    @Test
    public void producesValidSensorLog() {
        Faker faker = new Faker();
        int pressure = faker.number().numberBetween(-100000, 100000);
        float decimal = (float)faker.number().randomDouble(4, 0, 1);

        byte[] pressureBytes = Ints.toByteArray(pressure);

        Thingy52SensorConfig config = new Thingy52SensorConfig("some-temp-id");
        ReplayProcessor<SensorLog> processor = ReplayProcessor.create(10);

        PressureNotification notification = new PressureNotification(config, processor);
        // Typically we would have to watch for signing of bytes here, but we're within the range so its okay.
        notification.run(new byte[] { pressureBytes[3], pressureBytes[2], pressureBytes[1], pressureBytes[0], (byte)(decimal * 256) });

        SensorLog createdLog = processor.blockingFirst();

        assertThat(createdLog).isNotNull();
        assertThat(createdLog.getSensorId()).matches(config.id());
        assertThat(createdLog.getAttributeValue()).containsKey(PressureNotification.PRESSURE_VALUE_ID);
        assertThat((float)createdLog.getAttributeValue().get(PressureNotification.PRESSURE_VALUE_ID)).isWithin(0.01f).of(pressure + decimal);
    }
}
