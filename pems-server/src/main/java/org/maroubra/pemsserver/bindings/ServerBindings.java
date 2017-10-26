package org.maroubra.pemsserver.bindings;

import com.google.inject.AbstractModule;
import org.maroubra.pemsserver.control.ControlService;
import org.maroubra.pemsserver.control.ControlServiceImpl;
import org.maroubra.pemsserver.monitoring.MonitoringService;
import org.maroubra.pemsserver.monitoring.MonitoringServiceImpl;

import javax.activation.MimetypesFileTypeMap;
import javax.inject.Singleton;

public class ServerBindings extends AbstractModule {
    @Override
    protected void configure() {
        bind(MonitoringService.class).to(MonitoringServiceImpl.class).in(Singleton.class);
        bind(ControlService.class).to(ControlServiceImpl.class).in(Singleton.class);
        bind(MimetypesFileTypeMap.class).toInstance(new MimetypesFileTypeMap());
    }
}
