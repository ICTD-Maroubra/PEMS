package org.maroubra.pemsserver.monitoring.utsapi;

import org.junit.Test;
import org.maroubra.pemsserver.monitoring.SensorLog;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


import static com.google.common.truth.Truth.assertThat;

public class WebSensorTest {

    @Test
    public void testWebSensorStart() {

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
        UtsWebApi webApi = retrofit.create(UtsWebApi.class);

        WebSensor webSensor1 = new WebSensor(webSensorConfig1, webApi);
        webSensor1.setPollIntervalMinutes(360);
        webSensor1.start();
        SensorLog createdLog = webSensor1.logs().blockingFirst();

        if (createdLog != null) {
            assertThat(createdLog.getSensorId()).matches(webSensorConfig1.getId());
            assertThat(createdLog.getAttributeValue()).hasSize(1);
        }

        WebSensor webSensor2 = new WebSensor(webSensorConfig2, webApi);
        webSensor2.setPollIntervalMinutes(360);
        webSensor2.start();
        SensorLog createdLog2 = webSensor2.logs().blockingFirst();

        if (createdLog2 != null) {
            assertThat(createdLog.getSensorId()).matches(webSensorConfig1.getId());
            assertThat(createdLog.getAttributeValue()).hasSize(1);
        }

        WebSensor webSensor3 = new WebSensor(webSensorConfig3, webApi);
        webSensor3.setPollIntervalMinutes(360);
        webSensor3.start();
        SensorLog createdLog3 = webSensor3.logs().blockingFirst();

        if (createdLog3 != null) {
            assertThat(createdLog.getSensorId()).matches(webSensorConfig1.getId());
            assertThat(createdLog.getAttributeValue()).hasSize(1);
        }

   }

}


