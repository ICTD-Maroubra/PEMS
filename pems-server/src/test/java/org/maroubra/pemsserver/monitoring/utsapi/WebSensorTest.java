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

   }

}


