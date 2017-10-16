package org.maroubra.pemsserver.bluetooth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tinyb.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class BluetoothService {

    private static final Logger log = LoggerFactory.getLogger(BluetoothService.class);
    private static final AtomicInteger searching = new AtomicInteger(0);

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

        device = (BluetoothDevice) safeGetBluetoothObject(BluetoothType.DEVICE, null, address, null, Duration.ofSeconds(5));

        return device;
    }

    private BluetoothObject safeGetBluetoothObject(BluetoothType type, String name, String address, BluetoothObject parent, Duration searchFor) {
        searching.getAndIncrement();

        bluetoothManager.startDiscovery();

        BluetoothObject bluetoothObject = bluetoothManager.find(type, name, address, parent, searchFor);

        if (searching.decrementAndGet() < 1) {
            try {
                bluetoothManager.stopDiscovery();
            } catch (BluetoothException e) {
                log.warn("Bluetooth discovery could not be stopped.");
            }
        }

        return bluetoothObject;
    }
}
