package org.maroubra.pemsserver.control.actuators;

import org.maroubra.pemsserver.control.AbstractActuator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinearActuator extends AbstractActuator {
    private static final Logger logger = LoggerFactory.getLogger(SpringActuator.class);

    public LinearActuator(int id, String name) {
        super(id, name);
    }

    @Override
    public void operate() {
        logger.debug("Linear actuator is operating");
    }
}
