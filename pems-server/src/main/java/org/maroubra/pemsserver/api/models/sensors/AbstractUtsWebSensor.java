package org.maroubra.pemsserver.api.models.sensors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;


public class AbstractUtsWebSensor {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private int pollIntervalMinutes = 60;
    private String family;
    private String subSensor;
    private String sensor;
    private String apiLink = "http://eif-research.feit.uts.edu.au/api/";
    private String dataFormat = "json/";
    private String query = null;
    private static Logger log = LoggerFactory.getLogger(AbstractUtsWebSensor.class);


    /*example implementation - stick it to the main
        AbstractUtsWebSensor abstractUtsWebSensor = new AbstractUtsWebSensor("wasp","ES_B_11_429_3E90","BAT",120);
        abstractUtsWebSensor.setDates();
        abstractUtsWebSensor.buildHTTPQuery();
        log.info(abstractUtsWebSensor.getData());
     */

    public AbstractUtsWebSensor(String family, String sensor, String subSensor, int pollIntervalMinutes)
    {
        this.family = family;
        this.subSensor = subSensor;
        this.sensor = sensor;
        this.pollIntervalMinutes = pollIntervalMinutes;
    }

    public void setPollIntervalMinutes(int pollIntervalMinutes) {
        this.pollIntervalMinutes = pollIntervalMinutes;
    }

    public void setDates() {
        fromDate = LocalDateTime.now().minusMinutes(60).withNano(0);
        toDate = LocalDateTime.now().withNano(0);
    }

    public String getData()
    {
        String content = null;
        try {
            log.info(query);
            URL url = new URL(query);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            while ((content = in.readLine()) != null)
                System.out.println(content);
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public String buildHTTPQuery() {
        String partialQuery = "";
        Map<String,String> parameters = new LinkedHashMap<>();
        parameters.put("rFromDate",fromDate.toString());
        parameters.put("rToDate",toDate.toString());
        parameters.put("rFamily",family);
        parameters.put("rSensor",sensor);
        parameters.put("rSubSensor", subSensor);

        try {
            partialQuery = QueryBuilder.QueryBuilder(parameters);
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        query = apiLink + "" + dataFormat + "" + partialQuery;
        return query;
    }








}
