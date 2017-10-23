package org.maroubra.pemsserver.monitoring.utsapi;

import org.junit.Test;
import org.maroubra.pemsserver.monitoring.SensorLog;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


import static com.google.common.truth.Truth.assertThat;

public class WebSensorTest {

    @Test
    public void getSensorData() {

        WebSensor.Config webSensorConfig1 = new WebSensor.Config();
        webSensorConfig1.setId("Web001");
        webSensorConfig1.setConfig("wasp","ES_B_05_416_7C15","BAT");

        WebSensor.Config webSensorConfig2 = new WebSensor.Config();
        webSensorConfig2.setId("Web002");
        webSensorConfig2.setConfig("wasp","ES_B_11_429_3E90","BAT");

        WebSensor.Config webSensorConfig3 = new WebSensor.Config();
        webSensorConfig3.setId("Web003");
        webSensorConfig3.setConfig("wasp","ES_B_11_429_3E90","TCA");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://eif-research.feit.uts.edu.au/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        WebSensor webSensor1 = new WebSensor(webSensorConfig1, retrofit.create(UtsWebApi.class));
        webSensor1.start();
        SensorLog createdLog = webSensor1.logs().blockingFirst();

        assertThat(createdLog).isNotNull();
        assertThat(createdLog.getSensorId()).matches(webSensorConfig1.getId());
        assertThat(createdLog.getAttributeValue()).hasSize(2);

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


