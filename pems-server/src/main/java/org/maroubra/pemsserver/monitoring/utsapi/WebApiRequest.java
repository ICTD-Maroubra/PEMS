package org.maroubra.pemsserver.monitoring.utsapi;

import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

public interface WebApiRequest {


    @GET("json/")
    Call<List<String[]>> getJsonData(@QueryMap Map<String, String> options);

}
