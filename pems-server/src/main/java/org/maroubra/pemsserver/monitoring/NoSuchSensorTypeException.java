package org.maroubra.pemsserver.monitoring;

public class NoSuchSensorTypeException extends Exception {

    public NoSuchSensorTypeException() {
        super();
    }

    public NoSuchSensorTypeException(String s) {
        super(s);
    }

    public NoSuchSensorTypeException(String s, Throwable e) {
        super(s, e);
    }
}
