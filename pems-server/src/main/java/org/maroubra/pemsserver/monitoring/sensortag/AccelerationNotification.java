package org.maroubra.pemsserver.monitoring.sensortag;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tinyb.BluetoothNotification;

public class AccelerationNotification implements BluetoothNotification<byte[]> {


    private static final Logger log = LoggerFactory.getLogger(SensortagSensor.class);

    private final SensortagSensor.Config config;
    private final FlowableProcessor<SensorLog> processor;

    public AccelerationNotification(SensortagSensor.Config config, FlowableProcessor<SensorLog> processor) {
        this.config = config;
        this.processor = processor;
    }

    @Override
    public void run(byte[] bytes) {
        double [] acceleration = new double[3];

        final float scale = (float) 4096.0;

        log.info("The bytes: {}", bytes); //display all the bytes - only the first 6 has input.
        int x = shortSignedAtOffset(bytes, 6); //this should be the correct method but doesn't work atm
        int y = shortSignedAtOffset(bytes, 8);
        int z = shortSignedAtOffset(bytes, 10);

        acceleration [0] = x / scale * -1;
        acceleration [1] = y / scale;
        acceleration [2] = z / scale * -1;

        log.info("The acceleration: X: {}g, Y: {}g, Z: {}g", acceleration[0], acceleration[1], acceleration[2]);

        SensorLog sensorLog = new SensorLog(config.getId(), ImmutableMap.of("accel_x", acceleration[0], "accel_y", acceleration[1], "accel_z", acceleration[2]));

        processor.onNext(sensorLog);
    }

    private Integer shortSignedAtOffset(byte[] c, int offset)
    {
        Integer lowerByte = c[offset] & 0xFF;
        Integer upperByte = (int) c[offset + 1]; //Interpret MSB as singedan
        return (upperByte << 8) + lowerByte;
    }


}
