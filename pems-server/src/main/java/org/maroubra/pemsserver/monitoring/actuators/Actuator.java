package org.maroubra.pemsserver.monitoring.actuators;

public class Actuator {

    private int id;
    private String name;

    public Actuator() {}

    public Actuator(int id, String name) {
        this.id = id;
        this.name = name;
    }

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

        Actuator actuator = (Actuator) o;

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
