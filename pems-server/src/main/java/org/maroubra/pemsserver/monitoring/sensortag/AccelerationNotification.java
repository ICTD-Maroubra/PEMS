package org.maroubra.pemsserver.monitoring.sensortag;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.FlowableProcessor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tinyb.BluetoothNotification;

import java.text.DecimalFormat;

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

        final float scale = (float) (32768 / 8);

        double x = shortSignedAtOffset(bytes, 6);
        double y = shortSignedAtOffset(bytes, 8);
        double z = shortSignedAtOffset(bytes, 10);

        DecimalFormat df = new DecimalFormat("#.###");

        acceleration [0] = Double.parseDouble(df.format(x / scale * -1));
        acceleration [1] = Double.parseDouble(df.format(y / scale));
        acceleration [2] = Double.parseDouble(df.format(z / scale * -1));

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
