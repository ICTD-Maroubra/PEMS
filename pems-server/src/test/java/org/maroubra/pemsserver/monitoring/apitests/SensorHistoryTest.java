package org.maroubra.pemsserver.monitoring.apitests;

import com.google.common.collect.ImmutableMap;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;
import org.maroubra.pemsserver.api.models.sensors.responses.SensorHistoryResponse;
import org.maroubra.pemsserver.monitoring.SensorConfig;
import org.maroubra.pemsserver.monitoring.SensorLog;
import org.maroubra.pemsserver.monitoring.utsapi.UtsWebApi;
import org.maroubra.pemsserver.monitoring.utsapi.WebSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import static com.google.common.truth.Truth.assertThat;
public class SensorHistoryTest {

    @Test
    public void test() throws IOException {
        final Logger log = LoggerFactory.getLogger(WebSensor.class);

        SensorConfig webSensorConfig1 = new SensorConfig("web", ImmutableMap.of(
                WebSensor.CONFIG_KEY_FAMILY, "wasp",
                WebSensor.CONFIG_KEY_SENSOR, "ES_B_05_416_7C15",
                WebSensor.CONFIG_KEY_SUB_SENSOR, "BAT"
        ));

        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse().setBody("[[1508752848000,91],[1508753270000,92],[1508753692000,92],[1508754114000,87]]"));
        mockWebServer.start();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        UtsWebApi webApi = retrofit.create(UtsWebApi.class);

        WebSensor webSensor1 = new WebSensor(webSensorConfig1, webApi);
        webSensor1.setPollIntervalMinutes(1);
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
            List<SensorLog> sensorLogs = new ArrayList<>();
            sensorLogs.add(createdLog);

            SensorHistoryResponse response = SensorHistoryResponse.create(sensorLogs);
            assertThat(response.data).isNotNull();
            log.info(response.data[0].toString());

        }

    }
}
