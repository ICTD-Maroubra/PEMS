package org.maroubra.pemsserver.api.models.sensors;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class QueryBuilder {
    public static String QueryBuilder(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        //?rFromDate=2017-10-13T09%3A57%3A18
        // &rToDate=2017-10-15T09%3A57%3A18
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append("?");
            //rFromDate
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

}
