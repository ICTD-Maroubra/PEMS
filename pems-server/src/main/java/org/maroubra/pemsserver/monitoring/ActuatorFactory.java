package org.maroubra.pemsserver.monitoring;

import org.maroubra.pemsserver.monitoring.actuators.ElectricActuator;
import org.maroubra.pemsserver.monitoring.actuators.LinearActuator;
import org.maroubra.pemsserver.monitoring.actuators.SpringActuator;

public class ActuatorFactory {

    public static AbstractActuator getActuator(String actuatorType) {
        if (actuatorType == null) {
            return null;
        }
        if (actuatorType.equals("SPRING")) {
            return new SpringActuator(1, "ACTUATOR_SPRING");
        }
        if (actuatorType.equals("ELECTRIC")) {
            return new ElectricActuator(2, "ACTUATOR_ELECTRIC");
        }
        if (actuatorType.equals("LINEAR")) {
            return new LinearActuator(3, "ACTUATOR_LINEAR");
        }

        // should not reach here
        return null;
    }
}
