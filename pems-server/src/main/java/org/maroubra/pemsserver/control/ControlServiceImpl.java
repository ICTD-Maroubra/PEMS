package org.maroubra.pemsserver.control;

import com.google.common.collect.Lists;

import javax.inject.Inject;
import java.util.List;

public class ControlServiceImpl implements ControlService {

    private final List<AbstractActuator> runningActuators;

    @Inject
    public ControlServiceImpl() {
        this.runningActuators = Lists.newArrayList();
    }

    @Override
    public List<AbstractActuator> listActuators() {
        if (runningActuators.size()== 0) {
            initializeActuators();
        }
        return runningActuators;
    }

    private void initializeActuators() {
        runningActuators.add(ActuatorFactory.getActuator("SPRING"));
        runningActuators.add(ActuatorFactory.getActuator("LINEAR"));
        runningActuators.add(ActuatorFactory.getActuator("ELECTRIC"));
    }
}
