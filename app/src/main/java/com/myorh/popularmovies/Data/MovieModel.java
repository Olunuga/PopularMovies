package com.myorh.popularmovies.Data;

import java.net.URL;

/**
 * Created by Myorh on 6/7/2017.
 */

public class MovieModel {
    private String title,plot, release_date;
    private double user_rating;
    private long movieId;
    private String posterUrl;

    public MovieModel() {
    }

    public MovieModel(String title, String plot, String release_date, double user_rating, long movieId, String posterUrl) {
        this.title = title;
        this.plot = plot;
        this.release_date = release_date;
        this.user_rating = user_rating;
        this.movieId = movieId;
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getPlot() {
        return plot;
    }

    public String getRelease_date() {
        return release_date;
    }

    public double getUser_rating() {
        return user_rating;
    }

    public long getMovieId() {
        return movieId;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setUser_rating(float user_rating) {
        this.user_rating = user_rating;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
