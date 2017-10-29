package org.maroubra.pemsserver.monitoring;

import rx.Completable;
import rx.Observable;

import java.util.List;

public interface MonitoringService {

    Completable initializeSensors();
    Observable<SensorConfig> listSensors();
    List<Sensor.Descriptor> listSensorTypes();
    Completable createSensor(SensorConfig config);
    List<SensorLog> getSensorLogs(String sensorId, int limit);

}
