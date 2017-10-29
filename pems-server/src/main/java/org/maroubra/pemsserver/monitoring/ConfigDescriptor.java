package org.maroubra.pemsserver.monitoring;

import com.google.common.collect.Maps;
import org.maroubra.pemsserver.monitoring.configuration.ConfigField;

import java.util.Map;

public class ConfigDescriptor {

    private Map<String, ConfigField> fields = Maps.newLinkedHashMap();

    public ConfigDescriptor() {}

    public Map<String, ConfigField> getFields() {
        return fields;
    }

    public void addField(ConfigField field) {
        fields.put(field.getName(), field);
    }
}
