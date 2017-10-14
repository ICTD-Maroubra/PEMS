package org.maroubra.pemsserver.monitoring.sensortag;/*
    This class contains the list of sensors that are available in the CC2650 Ti SensorTag
*/

public class SensortagUUID {

	/* Barometer UUIDs */
    public static final String BARO_SENSOR_ENABLE = "f000aa40-0451-4000-b000-000000000000";
	public static final String BARO_SENSOR_DATA = "f000aa41-0451-4000-b000-000000000000";
	public static final String BARO_SENSOR_CONFIG = "f000aa42-0451-4000-b000-000000000000";
	public static final String BARO_SENSOR_CALI = "f000aa43-0451-4000-b000-000000000000";
	public static final String BARO_SENSOR_PERIOD = "f000aa44-0451-4000-b000-000000000000";

	/* Temperature UUIDs */
	public static final String TEMP_SENSOR_ENABLE = "f000aa00-0451-4000-b000-000000000000";
	public static final String TEMP_SENSOR_DATA = "f000aa01-0451-4000-b000-000000000000";
	public static final String TEMP_SENSOR_CONFIG = "f000aa02-0451-4000-b000-000000000000";
	public static final String TEMP_SENSOR_PERIOD = "f000aa03-0451-4000-b000-000000000000";

	/* Accelerometer UUIDs */
	public static final String ACCEL_SENSOR_ENABLE = "f000aa80-0451-4000-b000-000000000000";
	public static final String ACCEL_SENSOR_DATA = "f000aa81-0451-4000-b000-000000000000";
	public static final String ACCEL_SENSOR_CONFIG = "f000aa82-0451-4000-b000-000000000000";
	public static final String ACCEL_SENSOR_PERIOD = "f000aa83-0451-4000-b000-000000000000";

	/* Humidity UUIDs */
	public static final String HUMID_SENSOR_ENABLE = "f000aa20-0451-4000-b000-000000000000";
	public static final String HUMID_SENSOR_DATA = "f000aa21-0451-4000-b000-000000000000";
	public static final String HUMID_SENSOR_CONFIG = "f000aa22-0451-4000-b000-000000000000";
	public static final String HUMID_SENSOR_PERIOD = "f000aa23-0451-4000-b000-000000000000";

	/* Luxometer UUIDs */
	public static final String LUXO_SENSOR_ENABLE = "f000aa70-0451-4000-b000-000000000000";
	public static final String LUXO_SENSOR_DATA = "f000aa71-0451-4000-b000-000000000000";
	public static final String LUXO_SENSOR_CONFIG = "f000aa72-0451-4000-b000-000000000000";
	public static final String LUXO_SENSOR_PERIOD = "f000aa73-0451-4000-b000-000000000000";

	/* Other services found but not used
	f000ccc0-0451-4000-b000-000000000000 - Connection control service
	0000180a-0000-1000-8000-00805f9b34fb - Device Info Service
	f000ffc0-0451-4000-b000-000000000000 - OAT service
	f000aa64-0451-4000-b000-000000000000 - IO service
	f000ac00-0451-4000-b000-000000000000 - register service
	0000ffe0-0000-1000-8000-00805f9b34fb - Keypress service

	00001801-0000-1000-8000-00805f9b34fb - Unknown
	0000180f-0000-1000-8000-00805f9b34fb - Unknown
	*/
}