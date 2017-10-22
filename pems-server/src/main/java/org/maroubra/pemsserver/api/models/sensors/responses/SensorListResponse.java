package org.maroubra.pemsserver.api.models.sensors.responses;

// This section is hard codded as the Monitor System is not Intergrated;; Change to test the integrity of the system
public class SensorListResponse {
    public static int tempMax;
    public static int tempLow;
    public static int Wind;
    public static int CO2;
    public static int O2;
    public static int [] getdata() {
         int array[] = {
                tempMax = 45,
                tempLow = 45,
                Wind = 150,
                CO2 = 30,
                O2 = 60,
                };
        return array;
    }
}
