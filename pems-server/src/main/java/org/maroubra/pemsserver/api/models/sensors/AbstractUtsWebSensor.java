package org.maroubra.pemsserver.api.models.sensors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.net.URLConnection;


public class AbstractUtsWebSensor {
    protected LocalDateTime fromDate;
    protected LocalDateTime toDate;
    protected int pollIntervalMinutes = 60;
    protected String family;
    protected String subSensor;
    protected String sensor;
    protected String apiLink = "https://eif-research.feit.uts.edu.au/api/";
    protected String dataFormat = "json/";
    protected String query = null;
    private static Logger log = LoggerFactory.getLogger(AbstractUtsWebSensor.class);


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
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            content = conn.getContent().toString();

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

        }
        query = apiLink + "" + dataFormat + "" + partialQuery;
        return query;
    }








}
