package edu.northeastern.firebase.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A network utility class.
 */
public final class NetworkUtil {
    /**
     * Converts an input stream to a string.
     * The demo code of this module - FirebaseDemo3.java is referenced.
     *
     * @param inputStream the input stream
     * @return a string converted from the input steam
     */
    public static String convertStreamToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String len;
            while ((len = bufferedReader.readLine()) != null) {
                stringBuilder.append(len);
            }
            bufferedReader.close();

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Gets and converts the response of making a get request on the url provided.
     * The demo code of this module - FirebaseDemo3.java is referenced.
     *
     * @param url the url to be requested
     * @return a string representation of the response form the http request
     * @throws IOException if there is an IOException during the connecting process
     */
    public static String httpResponse(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        String response = NetworkUtil.convertStreamToString(inputStream);

        return response;
    }
}
