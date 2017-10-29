package org.maroubra.pemsserver.api.models.alarms.requests;

public class CreateAlarmRequest {
    private static int StartAlarm = 0;
    public static int StartAlarmRequest() {
        StartAlarm = 0;
        System.out.println("System Alarm Started");
        StartAlarm = 1;
        return StartAlarm;
    }
    public static int StopAlarmRequest() {
        StartAlarm = 1;
        System.out.println("System Alarm Request Stopped");
        StartAlarm = 0;
        return StartAlarm;
    }
}
