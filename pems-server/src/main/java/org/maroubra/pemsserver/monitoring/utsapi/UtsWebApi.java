package org.maroubra.pemsserver.monitoring.utsapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

public interface UtsWebApi {




    @GET("hc/{rFromDate}{rToDate}{rFamily}{rSensor}{rSubSensor}")
    Call<List<String[]>> getHcJsonData(@Path("rFromDate") String rFromDate, @Path("rToDate") String toDate,
                                   @Path("rFamily") String rFamily, @Path("rSensor") String rSensor,
                                   @Path("rSubSensor") String rSubSensor);

    /**
     * This call returns an array of sensor data in json format.
     * The parameters of query are provided by the WebSensor class.
     * @param options, LocalDateTime rFromDate, LocaLDateTime rToDate,
     *                 String rFamily, String rSensor, rSubSensor
     * The call link is build from the options parameters in reverse order.
     * ie. the first parameter rFromDate should be added last to the map.
     * @return List of sensor data arrays.
     */
    @GET("json/")
    Call<List<String[]>> getJsonData(@QueryMap Map<String, String> options);

    /**
     * This call returns an array of sensor data in hc json format.
     * The time stamps are in UTC format.
     * The parameters of query are provided by the WebSensor class.
     * @param options, LocalDateTime rFromDate, LocaLDateTime rToDate,
     *                 String rFamily, String rSensor, rSubSensor
     * The call link is build from the options parameters in reverse order.
     * ie. the first parameter rFromDate should be added last to the map.
     * @return List of sensor data arrays.
     */
    @GET("hc/")
    Call<List<String[]>> getHcJsonData(@QueryMap Map<String, String> options );

}
