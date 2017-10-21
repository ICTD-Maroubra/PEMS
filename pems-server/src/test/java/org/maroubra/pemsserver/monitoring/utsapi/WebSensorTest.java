package org.maroubra.pemsserver.monitoring.utsapi;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class WebSensorTest {

    @Test
    public void getSensorData() {
        WebSensorConfig webSensorConfig1 = new WebSensorConfig("Web001");
        webSensorConfig1.setConfig("wasp","ES_B_11_429_3E90","BAT");

        WebSensorConfig webSensorConfig2 = new WebSensorConfig("Web002");
        webSensorConfig2.setConfig("wasp","ES_B_11_429_3E90","BAT");

        WebSensorConfig webSensorConfig3 = new WebSensorConfig("Web003");
        webSensorConfig3.setConfig("wasp","ES_B_11_429_3E90","TCA");


        /*
        List<List<String[]>> dataList = new ArrayList<>();
        dataList.add(webSensor1.pollSensor());
        dataList.add(webSensor1.pollSensor());
        dataList.add(webSensor1.pollSensor());

        for (List<String[]> data : dataList) {
            if (data != null)
                assertThat(data.get(0)).hasLength(2);
        }
        */
   }

}


