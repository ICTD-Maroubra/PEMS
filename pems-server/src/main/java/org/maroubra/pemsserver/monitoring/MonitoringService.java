package org.maroubra.pemsserver.monitoring;

import rx.Completable;
import rx.Observable;

public interface MonitoringService {

    Completable initializeSensors();
    Observable<AbstractSensor> listSensors();
}
