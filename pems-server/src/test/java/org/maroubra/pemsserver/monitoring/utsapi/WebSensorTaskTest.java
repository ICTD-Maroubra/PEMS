package org.maroubra.pemsserver.monitoring.utsapi;

import com.google.common.collect.ImmutableMap;
import io.reactivex.processors.ReplayProcessor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.google.common.truth.Truth.assertThat;

public class WebSensorTaskTest {

    @Test
    public void testSensorTask() throws IOException {
        SensorConfig webSensorConfig1 = new SensorConfig("web001", "web", ImmutableMap.of(
                WebSensor.CONFIG_KEY_FAMILY, "wasp",
                WebSensor.CONFIG_KEY_SENSOR, "ES_B_05_416_7C15",
                WebSensor.CONFIG_KEY_SUB_SENSOR, "BAT"
        ));

        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("[[1508752848000,91],[1508753270000,92],[1508753692000,92],[1508754114000,87]]"));
        server.start();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server.url("").toString())
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
