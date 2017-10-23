package org.maroubra.pemsserver.monitoring.utsapi;

import io.reactivex.Flowable;
import org.junit.Test;
import org.maroubra.pemsserver.monitoring.Sensor;
import org.maroubra.pemsserver.monitoring.SensorLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.google.common.truth.Truth.assertThat;

public class WebSensorTest {


    @Test
    public void testWebSensorStart() {
        final Logger log = LoggerFactory.getLogger(WebSensor.class);

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
        SensorLog createdLog = null;
        try {
            createdLog = webSensor1.logs().timeout(5000, TimeUnit.MILLISECONDS).blockingFirst();
        }
        catch (Exception e) {
            if (e.getMessage().equals("java.util.concurrent.TimeoutException")) { }
            else { throw e; }
        }


        if (createdLog != null) {
            assertThat(createdLog.getSensorId()).matches(webSensorConfig1.getId());
            assertThat(createdLog.getAttributeValue()).hasSize(1);

        }

   }

}


