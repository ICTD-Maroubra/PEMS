package org.maroubra.pemsserver.api.models.sensors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;

public class WebSensorTask {
    private String query;

    public WebSensorTask(String query){
        this.query = query;

    }

    public String getData() throws Exception{
        return poll();
    }

    private String poll() {
        StringBuilder data = new StringBuilder();
        String content = null;
        try {
            URL url = new URL(query);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            while ((content = in.readLine()) != null)
                data.append(content);
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            content = "Error";
        }
        return data.toString();
    }
}
