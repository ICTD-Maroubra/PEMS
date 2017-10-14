import java.util.UUID;

/*
    This class contains the list of sensors that are available in the CC2650 Ti SensorTag
*/

public class sensortagUUID {

	/* Barometer Sensor */
	public static final String HANDLE_BARO_SENSOR_VALUE = "0x0031";
	public static final String HANDLE_BARO_SENSOR_NOTIFICATION = "0x0032";
	public static final String HANDLE_BARO_SENSOR_ENABLE = "0x0034";
	public static final String HANDLE_BARO_SENSOR_PERIOD =  "0x0036";

    public static final UUID UUID_BARO_SENSOR_ENABLE = UUID.fromString("f000aa40-0451-4000-b000-000000000000");
	public static final UUID UUID_BARO_SENSOR_DATA = UUID.fromString("f000aa41-0451-4000-b000-000000000000");
	public static final UUID UUID_BARO_SENSOR_CONFIG = UUID.fromString("f000aa42-0451-4000-b000-000000000000");
	public static final UUID UUID_BARO_SENSOR_CALI = UUID.fromString("f000aa43-0451-4000-b000-000000000000");
	public static final UUID UUID_BARO_SENSOR_PERIOD = UUID.fromString("f000aa44-0451-4000-b000-000000000000");

	/* Temperature Sensor */
	public static final String HANDLE_TEMP_SENSOR_VALUE = "0x0021";
	public static final String HANDLE_TEMP_SENSOR_NOTIFICATION = "0x0022";
	public static final String HANDLE_TEMP_SENSOR_ENABLE = "0x0024";
	public static final String HANDLE_TEMP_SENSOR_PERIOD =  "0x0026";

	public static final UUID UUID_TEMP_SENSOR_ENABLE = UUID.fromString("f000aa00-0451-4000-b000-000000000000");
	public static final UUID UUID_TEMP_SENSOR_DATA = UUID.fromString("f000aa01-0451-4000-b000-000000000000");
	public static final UUID UUID_TEMP_SENSOR_CONFIG = UUID.fromString("f000aa02-0451-4000-b000-000000000000");
	public static final UUID UUID_TEMP_SENSOR_PERIOD = UUID.fromString("f000aa03-0451-4000-b000-000000000000");

	/* Accelerometer Sensor */
	public static final String HANDLE_ACC_SENSOR_VALUE = "0x002d";
	public static final String HANDLE_ACC_SENSOR_NOTIFICATION = "0x002e";
	public static final String HANDLE_ACC_SENSOR_ENABLE = "0x0031";
	public static final String HANDLE_ACC_SENSOR_PERIOD =  "0x0034";

	public static final UUID UUID_ACC_SENSOR_ENABLE = UUID.fromString("f000aa80-0451-4000-b000-000000000000");
	public static final UUID UUID_ACC_SENSOR_DATA = UUID.fromString("f000aa81-0451-4000-b000-000000000000");
	public static final UUID UUID_ACC_SENSOR_CONFIG = UUID.fromString("f000aa82-0451-4000-b000-000000000000");
	public static final UUID UUID_ACC_SENSOR_PERIOD = UUID.fromString("f000aa83-0451-4000-b000-000000000000");

	/* Humidity Sensor */
	public static final String HANDLE_HUM_SENSOR_VALUE = "0x0020";
	public static final String HANDLE_HUM_SENSOR_NOTIFICATION = "0x002a";
	public static final String HANDLE_HUM_SENSOR_ENABLE = "0x002c";
	public static final String HANDLE_HUM_SENSOR_PERIOD =  "0x002E";

	public static final UUID UUID_HUM_SENSOR_ENABLE = UUID.fromString("f000aa20-0451-4000-b000-000000000000");
	public static final UUID UUID_HUM_SENSOR_DATA = UUID.fromString("f000aa21-0451-4000-b000-000000000000");
	public static final UUID UUID_HUM_SENSOR_CONFIG = UUID.fromString("f000aa22-0451-4000-b000-000000000000");
	public static final UUID UUID_HUM_SENSOR_PERIOD = UUID.fromString("f000aa23-0451-4000-b000-000000000000");

	/* Luxometer Sensor */
	public static final String HANDLE_LUXO_SENSOR_VALUE = "0x0041";
	public static final String HANDLE_LUXO_SENSOR_NOTIFICATION = "0x0042";
	public static final String HANDLE_LUXO_SENSOR_ENABLE = "0x0044";
	public static final String HANDLE_LUXO_SENSOR_PERIOD =  "0x0046";

	public static final UUID UUID_LUXO_SENSOR_ENABLE = UUID.fromString("f000aa70-0451-4000-b000-000000000000");
	public static final UUID UUID_LUXO_SENSOR_DATA = UUID.fromString("f000aa71-0451-4000-b000-000000000000");
	public static final UUID UUID_LUXO_SENSOR_CONFIG = UUID.fromString("f000aa72-0451-4000-b000-000000000000");
	public static final UUID UUID_LUXO_SENSOR_PERIOD = UUID.fromString("f000aa73-0451-4000-b000-000000000000");

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