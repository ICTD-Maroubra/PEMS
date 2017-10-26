package org.maroubra.pemsserver.control.automation;

import org.maroubra.pemsserver.control.AbstractActuator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Task implements Runnable, Comparable<Task> {

    private int id;
    private int priority;
    private TaskStatus status;
    private List<AbstractActuator> actuators;

    public Task() {
        actuators = new ArrayList<>();
    }

    public Task(int id, int priority, List<AbstractActuator> actuators) {
        this.id = id;
        this.priority = priority;
        this.actuators = actuators;
        this.status = TaskStatus.INACTIVE;
    }

    @Override
    public int compareTo(Task anotherTask) {

        if (this.priority == anotherTask.getPriority()) {
            return 0;
        }

        return this.priority > anotherTask.getPriority() ? -1 : 1;
    }

    /**
     * getters & setters
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public List<AbstractActuator> getActuators() {
        return actuators;
    }

    public void setActuators(List<AbstractActuator> actuators) {
        this.actuators = actuators;
    }

    public void addActuator(AbstractActuator actuator) { this.actuators.add(actuator); }

    public void removeActuator(AbstractActuator actuator) {
        if (actuator == null) {
            return;
        }
        Iterator<AbstractActuator> iterator = actuators.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(actuator)) {
                iterator.remove();
            }
        }
    }

}
