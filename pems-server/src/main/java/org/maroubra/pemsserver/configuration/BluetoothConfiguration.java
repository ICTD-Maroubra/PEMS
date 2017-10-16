package org.maroubra.pemsserver.configuration;

import com.github.joschi.jadconfig.Parameter;

public class BluetoothConfiguration {
    @Parameter(value = "ble_enabled")
    public boolean bluetoothEnabled = false;
}
