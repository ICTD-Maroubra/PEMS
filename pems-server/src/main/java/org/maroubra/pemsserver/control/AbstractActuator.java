package org.maroubra.pemsserver.control;

public abstract class AbstractActuator {

    private int id;
    private String name;

    public AbstractActuator() {}

    public AbstractActuator(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // implementation details of specific actuators
    public abstract void operate();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractActuator actuator = (AbstractActuator) o;

        if (id != actuator.id) return false;
        return name != null ? name.equals(actuator.name) : actuator.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
