package org.maroubra.pemsserver.monitoring;

import javax.inject.Inject;
import java.util.Map;

public class SensorFactory {

    private final Map<String, Sensor.Factory<? extends Sensor>> sensorFactories;

    @Inject
    public SensorFactory(Map<String, Sensor.Factory<? extends Sensor>> sensorFactories) {
        this.sensorFactories = sensorFactories;
    }

    public Sensor build(String type, SensorConfig config) throws NoSuchSensorTypeException {
        if (sensorFactories.containsKey(type)) {
            final Sensor.Factory<? extends Sensor> factory = sensorFactories.get(type);
            return factory.create(config);
        }

        throw new NoSuchSensorTypeException("There is no input of type <" + type + "> registered.");
    }
}
