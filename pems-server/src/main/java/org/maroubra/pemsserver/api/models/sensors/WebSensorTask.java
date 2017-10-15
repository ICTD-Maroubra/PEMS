package org.maroubra.pemsserver.api.models.sensors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;

public class WebSensorTask implements Callable{
    private static Logger log = LoggerFactory.getLogger(AbstractUtsWebSensorService.class);
    private String query;

    WebSensorTask(String query){
        this.query = query;

    }
    @Override
    public Object call() throws Exception {
        return getData();
    }

    public String getData() {
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
}
