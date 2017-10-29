package org.maroubra.pemsserver.control;

import com.google.common.collect.Lists;

import javax.inject.Inject;
import java.util.List;

public class ControlServiceImpl implements ControlService {

    private final ActuatorFactory actuatorFactory;
    private final List<AbstractActuator> runningActuators;

    @Inject
    public ControlServiceImpl(ActuatorFactory actuatorFactory) {
        this.actuatorFactory = actuatorFactory;
        this.runningActuators = Lists.newArrayList();
    }

    @Override
    public List<AbstractActuator> listActuators() {
        if (runningActuators == null)
            initializeActuators();
        return runningActuators;
    }

    private void initializeActuators() {
        runningActuators.add(ActuatorFactory.getActuator("SPRING"));
        runningActuators.add(ActuatorFactory.getActuator("LINEAR"));
        runningActuators.add(ActuatorFactory.getActuator("ELECTRIC"));
    }
}
