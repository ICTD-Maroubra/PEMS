package org.maroubra.pemsserver.api.models.alarms.responses;

import org.maroubra.pemsserver.api.models.UserOffset;
import org.maroubra.pemsserver.api.models.sensors.responses.SensorListResponse;

public class AlarmsListResponse {
    private static int Array[];
    private static int count = -1;
    public static int [] Response() {
        count = 0 ;
        if(SensorListResponse.tempMax >= UserOffset.tempMax) {
                Array[count++] =  "WARNING : High temperatures"+ SensorListResponse.tempMax;
        }
        if(SensorListResponse.tempLow >= UserOffset.tempLow) {
            Array[count++] =  "WARNING : Low temperatures"+ SensorListResponse.tempLow;
        }
        if(SensorListResponse.Wind >= UserOffset.Wind) {
                Array[count++] = "WARNING : High Wind Speeds"+ SensorListResponse.Wind;
        }
        if(SensorListResponse.CO2 >= UserOffset.CO2) {
                Array[count++] = "WARNING : High CO2 Levels"+ SensorListResponse.CO2;
        }
        if(SensorListResponse.O2 <= UserOffset.O2) {
            Array[count++] = "WARNING : Low Oxygen Levels"+ SensorListResponse.O2;
        }

        return Array;
    }
}
