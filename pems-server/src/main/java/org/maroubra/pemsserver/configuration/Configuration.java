package org.maroubra.pemsserver.configuration;

import com.github.joschi.jadconfig.JadConfig;
import com.github.joschi.jadconfig.RepositoryException;
import com.github.joschi.jadconfig.ValidationException;
import com.github.joschi.jadconfig.repositories.EnvironmentRepository;

public class Configuration {

    private static final String ENVIRONMENT_PREFIX = "PEMS_";

    private static ServerConfiguration serverConfiguration;
    private static MongoDbConfiguration mongoDbConfiguration;
    private static BluetoothConfiguration bluetoothConfiguration;

    public static ServerConfiguration getServerConfiguration() {
        if (serverConfiguration == null)
            loadConfigurations();

        return serverConfiguration;
    }

    public static MongoDbConfiguration getMongoDbConfiguration() {
        if (serverConfiguration == null)
            loadConfigurations();

        return mongoDbConfiguration;
    }

    public static BluetoothConfiguration getBluetoothConfiguration() {
        if (serverConfiguration == null)
            loadConfigurations();

        return bluetoothConfiguration;
    }

    private static void loadConfigurations() {
        JadConfig jadConfig = new JadConfig(new EnvironmentRepository(ENVIRONMENT_PREFIX), serverConfiguration, mongoDbConfiguration, bluetoothConfiguration);
        try {
            jadConfig.process();
        } catch (RepositoryException ex) {
            // TODO: log and better exception
        } catch (ValidationException ex) {
            // TODO: log and better exception
        }
    }
}
