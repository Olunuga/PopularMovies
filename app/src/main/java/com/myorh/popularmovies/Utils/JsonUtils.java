package com.myorh.popularmovies.Utils;

import com.myorh.popularmovies.Data.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Myorh on 6/7/2017.
 */

public class JsonUtils {

    public static List<MovieModel> getMovies(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray movieResponse = jsonObject.getJSONArray("results");
        List<MovieModel> movieList = new ArrayList<>();
        for(int i = 0; i < movieResponse.length(); i++){
           JSONObject singleMovie =  movieResponse.getJSONObject(i);
            MovieModel movieModel = new MovieModel(singleMovie.getString("original_title"),
                    singleMovie.getString("overview"),
                    singleMovie.getString("release_date"),
                   singleMovie.getDouble("vote_average"),
                    Long.parseLong(singleMovie.getString("id")),singleMovie.getString("poster_path"));
            movieList.add(movieModel);

        }
        return movieList;
    }
}
