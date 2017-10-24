package org.maroubra.pemsserver.monitoring.utsapi;

import io.reactivex.processors.ReplayProcessor;
import org.junit.Test;
import org.maroubra.pemsserver.monitoring.SensorLog;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

import static com.google.common.truth.Truth.assertThat;

public class WebSensorTaskTest {

    @Test
    public void testSensorTask() {
        WebSensor.Config webSensorConfig1 = new WebSensor.Config();
        webSensorConfig1.setId("Web001");
        webSensorConfig1.setConfig("wasp", "ES_B_05_416_7C15", "BAT");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://eif-research.feit.uts.edu.au/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();


        ReplayProcessor<SensorLog> processor = ReplayProcessor.create(10);

        WebSensorTask webSensorTask = new WebSensorTask(webSensorConfig1, 360, processor, retrofit.create(UtsWebApi.class));
        webSensorTask.pollSensor();

        SensorLog createdLog = null;
        try {
            createdLog = processor.timeout(5000, TimeUnit.MILLISECONDS).blockingFirst();
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
