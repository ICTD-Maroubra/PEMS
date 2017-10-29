package org.maroubra.pemsserver;
//sudo database
public class History {
    public static int tempMax;
    public static int tempLow;
    public static int Wind;
    public static int CO2;
    public static int O2;
    public static int [] sensorHistory() {
        //hard coded need database monitor layer link
        int array[] = {
                tempMax = 50,
                tempLow = 0,
                Wind = 250,
                CO2 = 30,
                O2 = 60,
        };
        return array;
    }

}
