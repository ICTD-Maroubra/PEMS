package org.maroubra.pemsserver.api.models.sensors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;

public class WebSensorTask implements Callable <String>{
    private String query;

    public WebSensorTask(String query){
        this.query = query;

    }
    @Override
    public String call() throws Exception{
        return getData();
    }

    private String getData() {
        String content = null;
        try {
            URL url = new URL(query);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            while ((content = in.readLine()) != null)
                System.out.println(content);
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            content = "Error";
        }
        return content;
    }
}
