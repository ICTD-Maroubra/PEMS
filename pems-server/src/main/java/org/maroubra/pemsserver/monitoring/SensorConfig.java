package org.maroubra.pemsserver.monitoring;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.maroubra.pemsserver.database.CollectionName;

import java.util.HashMap;
import java.util.Map;

@CollectionName("sensor_config")
public final class SensorConfig {

    private String id;
    private String type;
    private Map<String, Object> configMap;

    public SensorConfig(String type, Map<String, Object> configMap) {
        this.id = ObjectId.get().toString();
        this.type = type;
        this.configMap = configMap;
    }

    @BsonCreator
    public SensorConfig(@BsonProperty("id") String id, @BsonProperty("type") String type, @BsonProperty("configMap") HashMap<String, Object> configMap) {
        this(type, configMap);
        this.id = id;
    }

    @BsonId
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Map<String, Object> getConfigMap() {
        return configMap;
    }

    @BsonIgnore
    public String getStringProperty(String key) {
        return (String) configMap.get(key);
    }

    @BsonIgnore
    public Integer getIntegerProperty(String key) {
        return (Integer) configMap.get(key);
    }

    @BsonIgnore
    public Boolean getBooleanProperty(String key) {
        return (Boolean) configMap.get(key);
    }

    @BsonIgnore
    public Double getDoubleProperty(String key) {
        return (Double) configMap.get(key);
    }

    @BsonIgnore
    public Object getProperty(String key) {
        return configMap.get(key);
    }
}
