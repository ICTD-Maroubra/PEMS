package org.maroubra.pemsserver.bindings;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.maroubra.pemsserver.bluetooth.BluetoothService;
import tinyb.BluetoothManager;

public class BluetoothBindings extends AbstractBinder {
    @Override
    protected void configure() {
        bind(new BluetoothService(BluetoothManager.getBluetoothManager()));
    }
}
