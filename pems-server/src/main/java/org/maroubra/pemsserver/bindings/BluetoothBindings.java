package org.maroubra.pemsserver.bindings;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.maroubra.pemsserver.bluetooth.BluetoothService;
import org.maroubra.pemsserver.configuration.BluetoothConfiguration;
import org.maroubra.pemsserver.configuration.Configuration;
import tinyb.BluetoothManager;

public class BluetoothBindings extends AbstractBinder {
    @Override
    protected void configure() {
        BluetoothConfiguration configuration = Configuration.getBluetoothConfiguration();

        if (configuration.bluetoothEnabled) {
            System.loadLibrary("tinyb");
            System.loadLibrary("javatinyb");
            bind(new BluetoothService(BluetoothManager.getBluetoothManager()));
        }
    }
}
