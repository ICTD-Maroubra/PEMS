package org.maroubra.pemsserver.monitoring.sensortag;

import com.github.javafaker.Faker;
import io.reactivex.processors.ReplayProcessor;
import org.junit.Test;
import org.maroubra.pemsserver.monitoring.SensorLog;

import static com.google.common.truth.Truth.assertThat;

public class OpticalNotificationTest {

    @Test
    public void producesValidSensorLog() {
        Faker faker = new Faker();
        int mantissa = faker.number().numberBetween(0, 4096);
        int exponent = faker.number().numberBetween(0, 16);
        float lux = mantissa * (float)Math.pow(2, exponent) / 100.0f;

        SensortagSensor.Config config = new SensortagSensor.Config();
        config.setId("some-id");
        ReplayProcessor<SensorLog> processor = ReplayProcessor.create(10);

        OpticalNotification notification = new OpticalNotification(config, processor);
        // Typically we would have to watch for signing of bytes here, but we're within the range so its okay.
        notification.run(new byte[] { (byte)(mantissa & 0xff), (byte)((mantissa >> 8 & 0x0f) | (exponent << 4))});

        SensorLog createdLog = processor.blockingFirst();

        assertThat(createdLog).isNotNull();
        assertThat(createdLog.getSensorId()).matches(config.getId());
        assertThat(createdLog.getAttributeValue()).containsKey(OpticalNotification.LIGHT_INTENSITY_VALUE_ID);
        assertThat((float)createdLog.getAttributeValue().get(OpticalNotification.LIGHT_INTENSITY_VALUE_ID)).isWithin(0.1f).of(lux);
    }
}
