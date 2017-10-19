package org.maroubra.pemsserver.monitoring.nordic;

import com.github.javafaker.Faker;
import io.reactivex.processors.ReplayProcessor;
import org.junit.Test;
import org.maroubra.pemsserver.monitoring.SensorLog;

import static com.google.common.truth.Truth.assertThat;

public class HumidityNotificationTest {

    @Test
    public void producesValidSensorLog() {
        Faker faker = new Faker();
        int humidity = faker.number().numberBetween(0, 100);

        Thingy52SensorConfig config = new Thingy52SensorConfig("some-temp-id");
        ReplayProcessor<SensorLog> processor = ReplayProcessor.create(10);

        HumidityNotification notification = new HumidityNotification(config, processor);
        // Typically we would have to watch for signing of bytes here, but we're within the range so its okay.
        notification.run(new byte[] { (byte)(humidity & 0xff) });

        SensorLog createdLog = processor.blockingFirst();

        assertThat(createdLog).isNotNull();
        assertThat(createdLog.getSensorId()).matches(config.id());
        assertThat(createdLog.getAttributeValue()).containsExactly(HumidityNotification.HUMIDITY_VALUE_ID, humidity);
    }
}
