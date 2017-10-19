package org.maroubra.pemsserver.monitoring.nordic;

import com.github.javafaker.Faker;
import io.reactivex.processors.ReplayProcessor;
import org.junit.Test;
import org.maroubra.pemsserver.monitoring.SensorLog;

import static com.google.common.truth.Truth.assertThat;

public class ColorNotificationTest {

    @Test
    public void producesValidSensorLog() {
        Faker faker = new Faker();

        // I don't get how to generate unsigned bytes, so I've halved the range for now...
        int red = faker.number().numberBetween(0, 32768);
        int green = faker.number().numberBetween(0, 32768);
        int blue = faker.number().numberBetween(0, 32768);
        int clear = faker.number().numberBetween(0, 32768);

        Thingy52SensorConfig config = new Thingy52SensorConfig("some-temp-id");
        ReplayProcessor<SensorLog> processor = ReplayProcessor.create(10);

        ColorNotification notification = new ColorNotification(config, processor);
        notification.run(new byte[] { (byte)red, (byte)(red >> 8), (byte)green, (byte)(green >> 8), (byte)blue, (byte)(blue >> 8), (byte)clear, (byte)(clear >> 8) });

        SensorLog createdLog = processor.blockingFirst();

        assertThat(createdLog).isNotNull();
        assertThat(createdLog.getSensorId()).matches(config.id());
        assertThat(createdLog.getAttributeValue()).containsExactly(
                ColorNotification.COLOUR_RED_VALUE_ID, red,
                ColorNotification.COLOUR_GREEN_VALUE_ID, green,
                ColorNotification.COLOUR_BLUE_VALUE_ID, blue,
                ColorNotification.COLOUR_CLEAR_VALUE_ID, clear);
    }
}
