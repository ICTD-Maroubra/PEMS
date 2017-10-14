package org.maroubra.pemsserver.monitoring;

import com.mongodb.rx.client.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Completable;
import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class MonitoringServiceImpl implements MonitoringService {

    private static final Logger log = LoggerFactory.getLogger(MonitoringServiceImpl.class);

    private final MongoCollection<AbstractSensor> sensorCollection;
    private final MongoCollection<SensorLog> sensorLogsCollection;

    private List<AbstractSensor> runningSensors;

    @Inject
    public MonitoringServiceImpl(MongoCollection<AbstractSensor> sensorCollection, MongoCollection<SensorLog> sensorLogCollection) {
        this.sensorCollection = sensorCollection;
        this.sensorLogsCollection = sensorLogCollection;
    }

    @Override
    public Completable initializeSensors() {
        return listSensors().flatMap(sensor -> {
            if (sensor.start()) {
                runningSensors.add(sensor);
                sensor.logs().subscribe(this::recordSensorLog);
            }

            return null;
        }).toCompletable();
    }

    @Override
    public Observable<AbstractSensor> listSensors() {
        return sensorCollection.find().toObservable();
    }

    private void recordSensorLog(SensorLog sensorLog) {
        sensorLogsCollection.insertOne(sensorLog);
    }
}
