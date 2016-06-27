package com.amaze_ing.mm.amazeandroid.server_coms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
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

    /**
     * Send request string.
     *
     * @param resource    the resource to ask
     * @param params      the params to send with the request
     * @param method      the method (GET | POST)
     * @param isInSession the is in session in the web server
     * @return the json string result
     */
    public String sendRequest(String resource, HttpParameters params,
                                    String method, boolean isInSession) {
        String responseJson = "";

        try {
            // create the url with the params for sending the request
            URL url = new URL("http://advprog.cs.biu.ac.il:8080/AMaze/"
                                + resource + "?" + params.toParametersQuery());
            // connect to the url
            HttpURLConnection urlConnection = (HttpURLConnection)
                                                url.openConnection();
            if (isInSession)
                // add the cookie to the request
                urlConnection.setRequestProperty("Cookie", cookie);
            urlConnection.setRequestMethod(method);
            urlConnection.setDoOutput(true);
            if (!isInSession)
                // get the cookie from the request
                cookie = urlConnection.getHeaderField("Set-Cookie");

            // receive the response
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(
                                            urlConnection.getInputStream()));
                while ((line = br.readLine()) != null) {
                    responseJson+=line;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseJson;
    }

    /**
     * The type Http parameters.
     */
    public static class HttpParameters {
        /**
         * The Param names.
         */
        public ArrayList<String> paramNames;
        /**
         * The Param values.
         */
        public ArrayList<String> paramValues;

        /**
         * Instantiates a new Http parameters.
         */
        public HttpParameters() {
            this.paramNames = new ArrayList<>();
            this.paramValues = new ArrayList<>();
        }

        /**
         * Add http parameters.
         *
         * @param name  the name
         * @param value the value
         * @return the http parameters
         */
        public HttpParameters add(String name, String value) {
            this.paramNames.add(name);
            this.paramValues.add(value);
            return this;
        }

        /**
         * To parameters query string.
         *
         * @return the query string
         */
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
