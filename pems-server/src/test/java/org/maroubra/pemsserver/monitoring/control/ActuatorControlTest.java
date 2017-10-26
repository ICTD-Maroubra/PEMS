package org.maroubra.pemsserver.monitoring.control;

import org.junit.Test;
import org.maroubra.pemsserver.control.AbstractActuator;
import org.maroubra.pemsserver.control.ActuatorFactory;
import org.maroubra.pemsserver.control.actuators.*;
import org.maroubra.pemsserver.control.automation.Task;
import org.maroubra.pemsserver.control.automation.TaskManager;
import org.maroubra.pemsserver.control.automation.TaskPriority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ActuatorControlTest {

    Logger logger = LoggerFactory.getLogger(ActuatorControlTest.class);

    @Test
    public void actuatorControl() {

        List<AbstractActuator> actuators = new ArrayList<>();

        SpringActuator springActuator = (SpringActuator) ActuatorFactory.getActuator("SPRING");
        LinearActuator linearActuator = (LinearActuator) ActuatorFactory.getActuator("LINEAR");
        ElectricActuator electricActuator = (ElectricActuator) ActuatorFactory.getActuator("ELECTRIC");

        actuators.add(springActuator);
        actuators.add(linearActuator);
        actuators.add(electricActuator);

        Task myTask1 = new Task(1, TaskPriority.LOW, actuators) {
            @Override
            public void run() {
                logger.debug("task{} with {} priority is running", this.getId(), this.getPriority());
            }
        };
        Task myTask2 = new Task(2, TaskPriority.NORMAL, actuators) {
            @Override
            public void run() {
                logger.debug("task{} with {} priority is running", this.getId(), this.getPriority());
            }
        };
        Task myTask3 = new Task(3, TaskPriority.HIGH, actuators) {
            @Override
            public void run() {
                logger.debug("task{} with {} priority is running", this.getId(), this.getPriority());
            }
        };

        TaskManager manager = new TaskManager();

        // input order 2, 3, 1
        manager.add(myTask2);
        manager.add(myTask3);
        manager.add(myTask1);

        // regardless of the input order,
        // the order of outputs will always be 3, 2, 1
        manager.executeTasks();
    }
}
