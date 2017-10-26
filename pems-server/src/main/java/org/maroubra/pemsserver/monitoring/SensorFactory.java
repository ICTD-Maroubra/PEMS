package org.maroubra.pemsserver.monitoring;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<Sensor.Descriptor> availableSensorDescriptors() {
        return sensorFactories.entrySet().stream().map(factory -> factory.getValue().getDescriptor()).collect(Collectors.toList());
    }
}
