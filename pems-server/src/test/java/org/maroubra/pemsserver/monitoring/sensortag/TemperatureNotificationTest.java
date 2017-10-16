package org.maroubra.pemsserver.monitoring.sensortag;

import io.reactivex.processors.ReplayProcessor;
import org.junit.Test;
import org.maroubra.pemsserver.monitoring.SensorLog;

import static com.google.common.truth.Truth.assertThat;

import java.nio.ByteBuffer;

public class TemperatureNotificationTest {

    @Test
    public void producesValidSensorLog() {
        SensortagSensorConfig config = new SensortagSensorConfig("some-temp-id");
        ReplayProcessor<SensorLog> processor = ReplayProcessor.create(10);

        TemperatureNotification notification = new TemperatureNotification(config, processor);
        notification.run(ByteBuffer.allocate(4).putShort((short)22).putShort((short)15).array());

        SensorLog createdLog = processor.blockingFirst();

        assertThat(createdLog).isNotNull();
        assertThat(createdLog.getSensorId()).matches(config.id());
        assertThat(createdLog.getAttributeValue()).hasSize(2);
    }
}
