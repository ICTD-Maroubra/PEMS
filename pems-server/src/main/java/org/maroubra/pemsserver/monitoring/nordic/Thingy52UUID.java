package org.maroubra.pemsserver.monitoring.nordic;

import java.util.UUID;

/**
 * Contains UUIDs of bluetooth GATT services and characteristics
 * for the Thingy52.
 */
public class Thingy52UUID {
    public static final UUID UUID_WEATHER_SERVICE = UUID.fromString("EF680200-9B35-4933-9B10-52FFA9740042");
    public static final UUID UUID_TEMP_SENSOR_DATA = UUID.fromString("EF680201-9B35-4933-9B10-52FFA9740042");
    public static final UUID UUID_PRES_SENSOR_DATA = UUID.fromString("EF680202-9B35-4933-9B10-52FFA9740042");
    public static final UUID UUID_HUM_SENSOR_DATA = UUID.fromString("EF680203-9B35-4933-9B10-52FFA9740042");
    public static final UUID UUID_GAS_SENSOR_DATA = UUID.fromString("EF680204-9B35-4933-9B10-52FFA9740042");
    public static final UUID UUID_COLOR_SENSOR_DATA = UUID.fromString("EF680205-9B35-4933-9B10-52FFA9740042");

}
