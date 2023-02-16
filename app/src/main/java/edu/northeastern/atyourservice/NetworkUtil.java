package edu.northeastern.atyourservice;

import android.util.Patterns;
import android.webkit.URLUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ConcurrentNavigableMap;

public final class NetworkUtil {

    /**
     * Defines a self-defined exception for when the input url is invalid.
     */
    public static class InvalidUrlException extends Exception {
        /**
         * Non-argument constructor of the class.
         */
        public InvalidUrlException() {

        }

        /**
         * Constructor of the class.
         *
         * @param message the message of the exception
         */
        public InvalidUrlException(String message) {
            super(message);
        }
    }

    /**
     * Validates if the input url is a valid url.
     *
     * @param url the input url
     * @return a url that starts with "https://" or "http://"
     * @throws InvalidUrlException if the input url is invalid
     */
    public static String validInput(String url) throws InvalidUrlException {
        if (Patterns.WEB_URL.matcher(url).matches() || URLUtil.isValidUrl(url)) {
            if (url.startsWith("https://") || url.startsWith("http://")) {
                return url;
            }
            return "https://" + url;
        }

        throw new InvalidUrlException("Invalid url input.");
    }

    /**
     * Converts an input stream to a string.
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
