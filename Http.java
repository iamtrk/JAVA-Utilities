
package com.myntra.referral.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

public class Http {
    final static Logger logger = LoggerFactory.getLogger(Http.class);

    private static void disconnect(HttpURLConnection con) {
        try {
            if (con != null) {
                con.disconnect();
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
    }
    /* Method to make GET Requests, No 3rd party library has been
    used. Developed purely on Core JAVA. */
    public static String sendGet(String url) throws IOException {
        URL obj = new URL(url);
        int responseCode = -1;
        int tryCount = 0;
        HttpURLConnection con = null;

        StringBuilder response = new StringBuilder();

        do {
            try {
                disconnect(con);
                con = (HttpURLConnection) obj.openConnection();
                System.out.println(con.getConnectTimeout() +" time out is "+con.getReadTimeout());

                // add request header
                con.setRequestMethod("GET");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Content-Type", "application/json");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                responseCode = con.getResponseCode();
                in.close();
            } catch (IOException ex){
                if((++tryCount) >= 0) {
                    throw ex;
                }
            }
        } while (responseCode != 200);

        return response.toString();
    }
    /* Method to make POST Requests, No 3rd party library has been
    used. Developed purely on Core JAVA. */
    public static String sendPost(String url, String props, Map<String, String> headers) throws IOException {
        URL obj = new URL(url);
        int responseCode = -1;
        int tryCount = 0;
        HttpURLConnection con = null;


        do {
            disconnect(con);

            con = (HttpURLConnection) obj.openConnection();
            if (tryCount > 0) {

                // wait for 3 seconds and then try again
                try {
                    Thread.sleep(3000);
                } catch (Exception ex) {
                    logger.info(ex.getMessage());
                }
            }

            // add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json");
            if(headers!=null) {
                for (String key : headers.keySet()) {
                    con.setRequestProperty(key, headers.get(key));
                }
            }

            // Send post request
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(props);
            wr.flush();
            wr.close();

            responseCode = con.getResponseCode();

        } while (responseCode != 200 && (++tryCount) < 3);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return (response.toString());
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            System.out.println(Http.sendGet("http://google.com"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(System.currentTimeMillis() - start);
        }
    }
}
