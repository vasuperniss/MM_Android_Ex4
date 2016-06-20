package com.amaze_ing.mm.amazeandroid.server_coms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by user on 20/06/2016.
 */
public class HttpRequest {
    private static String cookie = null;

    public String sendRequest(String resource, HttpParameters params, String method, boolean isInSession) {
        String responseJson = null;
        try {
            URL url = new URL("http://advprog.cs.biu.ac.il:8080/AMaze/" + resource);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (isInSession)
                urlConnection.setRequestProperty("Cookie", cookie);
            urlConnection.setRequestMethod(method);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            // send the request
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            if (!isInSession)
                cookie = urlConnection.getHeaderField("Set-Cookie");
            writer.write(params.toParametersQuery());
            writer.flush();
            writer.close();

            // receive the response
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line=br.readLine()) != null) {
                    responseJson+=line;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseJson;
    }

    public static class HttpParameters {
        public ArrayList<String> paramNames;
        public ArrayList<String> paramValues;

        public HttpParameters() {
            this.paramNames = new ArrayList<>();
            this.paramValues = new ArrayList<>();
        }

        public HttpParameters add(String name, String value) {
            this.paramNames.add(name);
            this.paramValues.add(value);
            return this;
        }

        public String toParametersQuery() {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < this.paramNames.size(); ++i)
            {
                if (i != 0)
                    result.append("&");
                try {
                    result.append(URLEncoder.encode(this.paramNames.get(i), "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(this.paramValues.get(i), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            return result.toString();
        }
    }
}
