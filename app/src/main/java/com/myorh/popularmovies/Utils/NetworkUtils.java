package com.myorh.popularmovies.Utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static com.myorh.popularmovies.Utils.Constants.API_KEY;
import static com.myorh.popularmovies.Utils.Constants.BASE_URL;

/**
 * Created by Myorh on 6/6/2017.
 */

public class NetworkUtils {
    private static final String API_KEY_QUERY_KEY = "api_key";

    public static URL buildUrl(String path){
        Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendPath(path)
                .appendQueryParameter(API_KEY_QUERY_KEY,API_KEY).build();

        URL movieUrl = null;
        try {
            movieUrl = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return movieUrl;
    }

    public static String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try{
        InputStream in = httpURLConnection.getInputStream();
        Scanner scanner = new Scanner(in);

        scanner.useDelimiter("\\A");

        boolean hasInPut = scanner.hasNext();

        if (hasInPut){
            return scanner.next();
        }else {
            return null;
        }

         }finally {
            httpURLConnection.disconnect();
        }
    }
}
