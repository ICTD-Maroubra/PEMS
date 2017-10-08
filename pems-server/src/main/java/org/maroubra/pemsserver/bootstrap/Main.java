package org.maroubra.pemsserver.bootstrap;

import com.github.joschi.jadconfig.JadConfig;
import com.github.joschi.jadconfig.RepositoryException;
import com.github.joschi.jadconfig.ValidationException;
import com.github.joschi.jadconfig.repositories.EnvironmentRepository;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.maroubra.pemsserver.configuration.ServerConfiguration;
import org.maroubra.pemsserver.jersey.JerseyApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

public class Main {
    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
        ServerConfiguration serverConfiguration = loadConfiguration();

        log.info("Attempting to start application on " + serverConfiguration.fullHost());

        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(serverConfiguration.fullHost()), new JerseyApplication(), locator);

        try {
            httpServer.start();

            System.out.println(String.format("Jersey app started at %s", serverConfiguration.fullHost()));

            Runtime.getRuntime().addShutdownHook(new Thread(httpServer::shutdownNow));

            Thread.currentThread().join();
        } catch (IOException e) {
            log.error("Error starting server: " + e.getLocalizedMessage(), e);
        } catch (InterruptedException e) {
            log.error("Server crash: %s", e);
        }
    }

    private static ServerConfiguration loadConfiguration() {
        ServerConfiguration serverConfiguration = new ServerConfiguration();
        JadConfig jadConfig = new JadConfig(new EnvironmentRepository("PEMS_"), serverConfiguration);

        try {
            jadConfig.process();
        } catch (RepositoryException ex) {

        } catch (ValidationException ex) {

        }

        return serverConfiguration;
    }
}
