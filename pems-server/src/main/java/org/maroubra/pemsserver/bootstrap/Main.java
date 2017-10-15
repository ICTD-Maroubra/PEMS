package org.maroubra.pemsserver.bootstrap;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.servlet.GrizzlyWebContainerFactory;
import org.glassfish.jersey.servlet.ServletContainer;
import org.maroubra.pemsserver.configuration.Configuration;
import org.maroubra.pemsserver.configuration.ServerConfiguration;
import org.maroubra.pemsserver.jersey.JerseyApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

public class Main {
    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ServerConfiguration serverConfiguration = Configuration.getServerConfiguration();

        log.info("Attempting to start application on " + serverConfiguration.fullHost());

        try {
            ServletContainer sc = new ServletContainer(new JerseyApplication(serverConfiguration));
            HttpServer httpServer = GrizzlyWebContainerFactory.create(URI.create(serverConfiguration.fullHost()), sc, null, null);

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
}
