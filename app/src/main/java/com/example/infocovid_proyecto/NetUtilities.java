package com.example.infocovid_proyecto;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtilities {

    private static final String LOG_TAG=NetUtilities.class.getSimpleName();
    private static String BOOK_URL="";

    public static String getCases(String url) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonBook = null;
        BOOK_URL = url;

        try {
            Uri buildUri = Uri.parse(BOOK_URL).buildUpon().build();

            URL requestUrl = new URL(buildUri.toString());
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            if (builder.length() == 0) {
                return null;
            }
            jsonBook = builder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, jsonBook);
        return jsonBook;
    }
}
