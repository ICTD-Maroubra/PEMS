package org.maroubra.pemsserver.bindings;

import com.google.inject.AbstractModule;
import org.maroubra.pemsserver.bluetooth.BluetoothService;
import org.maroubra.pemsserver.configuration.BluetoothConfiguration;
import org.maroubra.pemsserver.configuration.Configuration;
import tinyb.BluetoothManager;

public class BluetoothBindings extends AbstractModule {
    @Override
    protected void configure() {
        BluetoothConfiguration configuration = Configuration.getBluetoothConfiguration();

        if (configuration.bluetoothEnabled) {
            System.loadLibrary("tinyb");
            System.loadLibrary("javatinyb");
            bind(BluetoothManager.class).toInstance(BluetoothManager.getBluetoothManager());
        }

        bind(BluetoothService.class).toInstance(new BluetoothService(null));
    }
}
