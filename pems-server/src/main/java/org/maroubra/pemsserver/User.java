package org.maroubra.pemsserver;

public class User {
    private static int TempMaxOffsetSlider;
    private static int TempMinOffsetSlider;
    private static int ActuatorActivate;
    private static int ActuatorTempMaxControlOffset;
    private static int ActuatorTempMinControlOffset;

    public static int commands() {

    }

    public static void ActutorInit() {
        //need get
        ActuatorTempMaxControlOffset = 50;
        ActuatorTempMinControlOffset = 50;
        ActuatorActivate();
    }
    public static int  ActuatorActivate() {
        return ActuatorActivate;
    }
    public static void TempSlider() {
        TempMaxOffsetSlider = 25;
        TempMinOffsetSlider = 20;
    }

}
