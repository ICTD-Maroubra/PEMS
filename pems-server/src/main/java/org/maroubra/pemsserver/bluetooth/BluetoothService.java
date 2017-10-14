package org.maroubra.pemsserver.bluetooth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tinyb.BluetoothDevice;
import tinyb.BluetoothException;
import tinyb.BluetoothManager;
import tinyb.BluetoothType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Duration;

@Singleton
public class BluetoothService {

    private static final Logger log = LoggerFactory.getLogger(BluetoothService.class);

    private final BluetoothManager bluetoothManager;

    @Inject
    public BluetoothService(BluetoothManager bluetoothManager) {
        this.bluetoothManager = bluetoothManager;
    }

    public BluetoothDevice getDevice(String address) throws InterruptedException {
        return getDevice(address, false);
    }

    public BluetoothDevice getDevice(String address, boolean scan) throws InterruptedException {
        BluetoothDevice device = bluetoothManager.getDevices().stream().filter(d -> d.getAddress().equals(address)).findFirst().orElse(null);
        if (device != null)
            return device;

        if (!scan)
            return null;

        bluetoothManager.startDiscovery();

        device = (BluetoothDevice) bluetoothManager.find(BluetoothType.DEVICE, null, address, null, Duration.ofSeconds(5));

        try {
            bluetoothManager.stopDiscovery();
        } catch (BluetoothException e) {
            log.warn("Bluetooth discovery could not be stopped.");
        }

        return device;
    }
}
