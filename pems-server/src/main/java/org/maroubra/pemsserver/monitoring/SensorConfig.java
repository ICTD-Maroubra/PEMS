package org.maroubra.pemsserver.monitoring;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.maroubra.pemsserver.database.CollectionName;

import java.util.HashMap;
import java.util.Map;

@CollectionName("sensor_config")
public final class SensorConfig {

    private String id;
    private String type;
    private Map<String, String> configMap;

    public SensorConfig(String id, String type, Map<String, String> configMap) {
        this.id = id;
        this.type = type;
        this.configMap = configMap;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Map<String, String> getConfigMap() {
        return configMap;
    }

    @BsonIgnore
    public String getProperty(String key) {
        return configMap.get(key);
    }

    @BsonCreator
    public static SensorConfig create(@BsonProperty("id") String id, @BsonProperty("type") String type, @BsonProperty("configMap") HashMap<String, String> configMap) {
        return new SensorConfig(id, type, configMap);
    }
}
