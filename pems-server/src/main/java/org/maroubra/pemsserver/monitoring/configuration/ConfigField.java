package org.maroubra.pemsserver.monitoring.configuration;

public class ConfigField {

    private final String name;
    private final FieldType type;
    private final Boolean required;
    private final String description;

    private ConfigField(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.required = builder.required;
        this.description = builder.description;
    }

    public String getName() {
        return name;
    }

    public FieldType getType() {
        return type;
    }

    public Boolean getRequired() { return required; }

    public String getDescription() {
        return description;
    }

    public static Builder builder(String name) {
        return new Builder(name);
    }

    public static class Builder {
        private final String name;
        private FieldType type = FieldType.STRING;
        private boolean required = false;
        private String description;

        public Builder(String name) {
            this.name = name;
        }

        public Builder type(FieldType type) {
            this.type = type;
            return this;
        }

        public Builder required(boolean required) {
            this.required = required;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public ConfigField build() {
            return new ConfigField(this);
        }
    }
}
