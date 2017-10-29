package org.maroubra.pemsserver.monitoring.control;

import org.junit.Test;
import org.maroubra.pemsserver.control.AbstractActuator;
import org.maroubra.pemsserver.control.ControlService;
import org.maroubra.pemsserver.control.ControlServiceImpl;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class ControlServiceTest {
    @Test
    public void listActuatorsTest() {
        ControlService service = new ControlServiceImpl();
        List<AbstractActuator> actuatorList = service.listActuators();

        assertThat(actuatorList).isNotNull();
        assertThat(actuatorList).hasSize(3);
    }
}
