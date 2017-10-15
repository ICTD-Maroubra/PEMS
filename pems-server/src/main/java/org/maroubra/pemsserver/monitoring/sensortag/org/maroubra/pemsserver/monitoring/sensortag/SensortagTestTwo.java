package org.maroubra.pemsserver.monitoring.sensortag;

import tinyb.*;

import java.util.List;
import java.util.Map;

import static org.maroubra.pemsserver.monitoring.sensortag.SensortagUUID.*;
import static org.maroubra.pemsserver.monitoring.sensortag.sensortagTest.convertCelsius;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


public class SensortagTestTwo {

    //private static final Logger logger = LoggerFactory.getLogger(sensortagTestTwo.class);

    public static final String MAC_ADDRESS = "B0:91:22:EA:5C:82";
    private static final float SCALE_LSB = 0.03125f;
    static boolean running = true;
    private static BluetoothDevice sensorTagDevice;
    private Map<String, BluetoothGattService> gattServices;


    static void printDevice(BluetoothDevice device) {
        System.out.print("Address = " + device.getAddress());
        System.out.print(" Name = " + device.getName());
        System.out.print(" Connected = " + device.getConnected());
        System.out.println();
    }

    private static BluetoothDevice getDevice(String address) throws InterruptedException {
        BluetoothManager manager = BluetoothManager.getBluetoothManager();
        for (int i = 0; (i < 15) && running; ++i) {
            List<BluetoothDevice> list = manager.getDevices();
            if (list == null)
                return null;

            for (BluetoothDevice device : list) {
                if (device.getAddress().equals(address))
                    sensorTagDevice = device;
            }

            if (sensorTagDevice != null) {
                return sensorTagDevice;
            }
            Thread.sleep(4000);
        }
        return null;
    }

    private static BluetoothGattService getService(BluetoothDevice device, String UUID) throws InterruptedException {
        BluetoothGattService tempService = null;
        List<BluetoothGattService> bluetoothServices = null;
        do {
            bluetoothServices = device.getServices();
            if (bluetoothServices == null)
                return null;

            for (BluetoothGattService service : bluetoothServices) {
                if (service.getUUID().equals(UUID)) {
                    tempService = service;
                    System.out.println("Service acquired");
                }
            }
            Thread.sleep(4000);
        } while (bluetoothServices.isEmpty() && running);
        return tempService;
    }

    static BluetoothGattCharacteristic getCharacteristic(BluetoothGattService service, String UUID) {
        List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
        if (characteristics == null)
            return null;

        for (BluetoothGattCharacteristic characteristic : characteristics) {
            if (characteristic.getUUID().equals(UUID))
                return characteristic;
        }
        return null;
    }

 public static void main(String[] args) throws InterruptedException {

        BluetoothManager manager = BluetoothManager.getBluetoothManager();

        boolean discoveryStarted = manager.startDiscovery();
        System.out.println("The discovery started: " + (discoveryStarted ? "true" : "false"));
        BluetoothDevice sensor = getDevice(MAC_ADDRESS);

        try {
            manager.stopDiscovery();
        } catch (BluetoothException e) {
            System.err.println("Discovery could not be stopped.");
        }

        if (sensor == null) {
            System.err.println("No sensor found with the provided address.");
            System.exit(-1);
        }

        System.out.print("Found device: ");
        printDevice(sensor);

        BluetoothGattService tempService = serviceDiscovery(sensor);
        if(enableThermometer())
            readTemperature();
        else
            System.exit(1);
 }



    private static BluetoothGattService serviceDiscovery(BluetoothDevice sensorTag){
        System.out.println("Services exposed by device:");
        BluetoothGattService tempService = null;
        List<BluetoothGattService> bluetoothServices = null;
            do {
            bluetoothServices = sensorTag.getServices();
            if (bluetoothServices == null)
                return null;

            for (BluetoothGattService service : bluetoothServices) {
                System.out.println("UUID: " + service.getUUID());
                //if UUID matches -> turn on that sensor
            }
        } while (bluetoothServices.isEmpty() && running);
            return null;
    }

    public static boolean enableThermometer() {
        byte[] config = { 0x01 };
        try {
            BluetoothGattService tempService = getService(sensorTagDevice, UUID_TEMP_SENSOR_SERVICE);

            BluetoothGattCharacteristic tempValue = getCharacteristic(tempService, UUID_TEMP_SENSOR_DATA);
            BluetoothGattCharacteristic tempConfig = getCharacteristic(tempService, UUID_TEMP_SENSOR_ENABLE);
            BluetoothGattCharacteristic tempPeriod = getCharacteristic(tempService, UUID_TEMP_SENSOR_PERIOD);

            if (tempValue == null || tempConfig == null || tempPeriod == null) {
                System.err.println("Could not find the correct characteristics.");
                return false;
            }
            tempConfig.writeValue(config);
            tempPeriod.writeValue(new byte[] {0x64});
            System.out.print("Thermometer enabled");
        } catch (Exception e) {
            //log - cant enable thermometer
        }
        return true;
    }

    public void disableThermometer()
    {
        byte[] config = { 0x00 };
        BluetoothGattCharacteristic tempEnableChar;
        try {

                 } catch (Exception e) {
            //logger.error("Thermometer disable failed", e");
        }
    }

    private static void readTemperature() {
        double[] temperatures = new double[2];
        BluetoothGattCharacteristic tempValueChar;
        try {
            BluetoothGattService tempService = getService(sensorTagDevice, UUID_TEMP_SENSOR_SERVICE);
            tempValueChar = getCharacteristic(tempService, UUID_TEMP_SENSOR_DATA);

            while (running) {
                byte[] tempRaw = tempValueChar.readValue();
                System.out.print("Temp raw = {");
                for (byte b : tempRaw) {
                    System.out.print(String.format("%02x,", b));
                }
                System.out.print("}");

            /*
             * The temperature service returns the data in an encoded format which can be found in the wiki. Convert the
             * raw temperature format to celsius and print it. Conversion for object temperature depends on ambient
             * according to wiki, but assume result is good enough for our purposes without conversion.
             */
                int objectTempRaw = (tempRaw[0] & 0xff) | (tempRaw[1] << 8);
                int ambientTempRaw = (tempRaw[2] & 0xff) | (tempRaw[3] << 8);

                float objectTempCelsius = convertCelsius(objectTempRaw);
                float ambientTempCelsius = convertCelsius(ambientTempRaw);

                System.out.println(
                        String.format(" Temp: Object = %fC, Ambient = %fC", objectTempCelsius, ambientTempCelsius));
            }
        } catch (Exception e) {
            System.err.println("Doesn't work");
        }
    }



    private void readAcceleration(){

    }

    private void readHumidity(){

    }

    private void readPressure(){

    }

    private void readLight()
    {

    }

    private void waitFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            //log error.

        }

    }



}
