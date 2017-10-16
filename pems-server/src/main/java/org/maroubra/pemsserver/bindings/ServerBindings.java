package org.maroubra.pemsserver.bindings;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.maroubra.pemsserver.monitoring.MonitoringService;
import org.maroubra.pemsserver.monitoring.MonitoringServiceImpl;

import javax.activation.MimetypesFileTypeMap;
import javax.inject.Singleton;

public class ServerBindings extends AbstractBinder {
    @Override
    protected void configure() {
        bind(MonitoringService.class).to(MonitoringServiceImpl.class).in(Singleton.class);
        bind(new MimetypesFileTypeMap()).to(MimetypesFileTypeMap.class);
    }
}
