package org.maroubra.pemsserver.api.models.sensors;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AbstractUtsWebSensor {
    protected Date fromDate;
    protected Date toDate;
    protected String family;
    protected String subSensor;
    protected String sensor;
    protected String apiLink = "https://eif-research.feit.uts.edu.au/api/";
    protected String dataFormat = "json/";
    protected String query = null;


    private String buildHTTPQuery()
    {
        Map<String,String> parameters = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy'T'hh:mm:ss");

        parameters.put("rFromDate", dateFormat.format(fromDate));
        parameters.put("rToDate",dateFormat.format(toDate));
        parameters.put("rFamily",family);
        parameters.put("rSensor",sensor);
        parameters.put("rSubSensor", subSensor);

        try {
            query = QueryBuilder.QueryBuilder(parameters);
        }
        catch (UnsupportedEncodingException e){

        }
        return query;
    }




    //https://eif-research.feit.uts.edu.au/api/json/?rFromDate=2017-10-13T09%3A57%3A18&rToDate=2017-10-15T09%3A57
    // %3A18&rFamily=wasp&rSensor=ES_B_11_429_3E90&rSubSensor=BAT




}
