package org.maroubra.pemsserver.monitoring;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoCollection;
import org.maroubra.pemsserver.database.MongoCollectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Completable;
import rx.Observable;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class MonitoringServiceImpl implements MonitoringService {

    private static final Logger log = LoggerFactory.getLogger(MonitoringServiceImpl.class);

    private final SensorFactory sensorFactory;
    private final MongoCollection<SensorConfig> sensorConfigCollection;
    private final MongoCollection<SensorLog> sensorLogsCollection;

    private List<Sensor> runningSensors = new ArrayList<>();

    public MonitoringServiceImpl(SensorFactory sensorFactory, MongoCollection<SensorConfig> sensorConfigCollection, MongoCollection<SensorLog> sensorLogCollection) {
        this.sensorFactory = sensorFactory;
        this.sensorConfigCollection = sensorConfigCollection;
        this.sensorLogsCollection = sensorLogCollection;
    }

    @Inject
    public MonitoringServiceImpl(SensorFactory sensorFactory, MongoCollectionFactory collectionFactory) {
        this(sensorFactory, collectionFactory.getCollection(SensorConfig.class), collectionFactory.getCollection(SensorLog.class));
    }

    @Override
    public Completable initializeSensors() {
        return listSensors().flatMap(sensorConfig -> {
            try {
                Sensor sensor = sensorFactory.build(sensorConfig.getType(), sensorConfig);
                startSensor(sensor);
            } catch (NoSuchSensorTypeException ex) {
                log.error("Could not start sensor with getId {}, its type was not found.", sensorConfig.getId());
            }

            return null;
        }).toCompletable();
    }

    @Override
    public Observable<SensorConfig> listSensors() {
        return sensorConfigCollection.find().toObservable();
    }

    @Override
    public List<Sensor.Descriptor> listSensorTypes() {
        return sensorFactory.availableSensorDescriptors();
    }

    @Override
    public Completable createSensor(SensorConfig config) {
        Sensor sensor;
        try {
            sensor = sensorFactory.build(config.getType(), config);
        } catch (NoSuchSensorTypeException ex) {
            log.error("Could not start sensor with getId {}, its type was not found.", config.getId());
            return Completable.error(ex);
        }

        return sensorConfigCollection.insertOne(config).toCompletable().doOnCompleted(() -> startSensor(sensor));
    }

    public List<SensorLog> getSensorLogs(String sensorId, int limit) {
        return sensorLogsCollection.find(Filters.eq("sensorId",sensorId)).limit(limit).toObservable().toList().toBlocking().single();
    }

    public boolean stopSensor (String id) {
        for (Sensor sensor: runningSensors) {
            if (sensor.getConfig().getId().equals(id)) {
                runningSensors.remove(sensor);
                return sensor.stop();
            }
        }
        return false;
    }

    public boolean startSensor (String id) {
        SensorConfig sensorConfig = sensorConfigCollection.find(Filters.eq("_id",id)).toObservable().toBlocking().single();
        Sensor sensor = null;
        try {
           sensor =  sensorFactory.build(sensorConfig.getType(), sensorConfig);
        }
        catch (NoSuchSensorTypeException e) {
            e.printStackTrace();
            return false;
        }
        if (sensor.start()) {
            runningSensors.add(sensor);
            return true;
        }
        return false;
    }

    private void startSensor(Sensor sensor) {
        if (sensor.start()) {
            runningSensors.add(sensor);
            sensor.logs().subscribe(this::recordSensorLog);
        }
    }

    private void recordSensorLog(SensorLog sensorLog) {
        sensorLogsCollection.insertOne(sensorLog).toBlocking().single();
    }
}
