package org.maroubra.pemsserver.monitoring.control;

import org.junit.Test;
import org.maroubra.pemsserver.monitoring.actuators.Actuator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ActuatorControlTest {

    @Test
    public void actuatorControl() {

        Logger logger = LoggerFactory.getLogger(ActuatorControlTest.class);

        Actuator actuator1 = new Actuator(1, "actuator 1");
        Actuator actuator2 = new Actuator(2, "actuator 2");
        List<Actuator> actuators = new ArrayList<>();
        actuators.add(actuator1);
        actuators.add(actuator2);
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
        manager.add(myTask2);
        manager.add(myTask3);
        manager.add(myTask1);
        manager.executeTasks();
    }
}
