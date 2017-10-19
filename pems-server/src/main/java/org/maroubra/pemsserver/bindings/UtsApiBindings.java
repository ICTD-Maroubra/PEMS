package org.maroubra.pemsserver.bindings;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.maroubra.pemsserver.monitoring.utsapi.UtsWebApi;
import org.maroubra.pemsserver.monitoring.utsapi.WebSensor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class UtsApiBindings extends AbstractBinder {

    @Override
    protected void configure() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://eif-research.feit.uts.edu.au/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        bind(retrofit.create(UtsWebApi.class));
    }
}
