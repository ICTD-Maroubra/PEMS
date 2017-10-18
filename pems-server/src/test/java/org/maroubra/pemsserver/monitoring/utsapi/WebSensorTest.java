package org.maroubra.pemsserver.monitoring.utsapi;


import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class WebSensorTest {
    public void getSensorData() {
        WebSensor webSensor1 = new  WebSensor("wasp","ES_B_11_429_3E90","BAT");
        WebSensor webSensor2 = new WebSensor("wasp", "ES_B_05_174_7BE3", "BAT");
        WebSensor webSensor3 = new WebSensor("wasp","ES_B_11_429_3E90","TCA");
        List<List<String[]>> dataList = new ArrayList<>();
        dataList.add(webSensor1.pollSensor());
        dataList.add(webSensor1.pollSensor());
        dataList.add(webSensor1.pollSensor());

        for (List<String[]> data : dataList) {
            assertThat(data.get(0)).hasLength(2);
        }
   }

}


