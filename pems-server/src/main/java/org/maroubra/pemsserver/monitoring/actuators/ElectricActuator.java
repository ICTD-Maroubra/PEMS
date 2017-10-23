package org.maroubra.pemsserver.monitoring.actuators;

import org.maroubra.pemsserver.monitoring.AbstractActuator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElectricActuator extends AbstractActuator {
    private static final Logger logger = LoggerFactory.getLogger(SpringActuator.class);

    public ElectricActuator(int id, String name) {
        super(id, name);
    }

    @Override
    public void operate() {
        logger.debug("Electric actuator is operating");
    }
}
