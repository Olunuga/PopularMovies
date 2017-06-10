package com.myorh.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myorh.popularmovies.Data.MovieModel;
import com.myorh.popularmovies.Utils.Constants;
import com.myorh.popularmovies.Utils.JsonUtils;
import com.myorh.popularmovies.Utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import static com.myorh.popularmovies.Utils.NetworkUtils.buildUrl;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener{
    ProgressBar mProgressBar;
    TextView tvErrorDisplay;
    RecyclerView recyclerView;
    List<MovieModel> movieList;
    MovieAdapter movieAdapter;
    GridLayoutManager layoutManager;
    Context context;
    SharedPreferences sharedPreferences;
    MovieAdapter.MovieClickListener movieClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_progress);
        tvErrorDisplay = (TextView) findViewById(R.id.tv_error_message);
        context = this;
        movieClickListener = this;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        movieList = null;
        recyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);

        movieAdapter = new MovieAdapter(context);
        movieAdapter.setListener(movieClickListener);
        layoutManager = new GridLayoutManager(getBaseContext(),2, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.setdata(null);
        fetchData(sharedPreferences.getString(getString(R.string.pref_sort_option_key),
                getString(R.string.pref_sort_option_popular_value)));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        
    }

    @Override
    public void OnMovieClick(MovieModel movieModel) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constants.KEY_TITLE,movieModel.getTitle());
        intent.putExtra(Constants.KEY_PLOT,movieModel.getPlot());
        intent.putExtra(Constants.KEY_RELEASE_DATE,movieModel.getRelease_date());
        intent.putExtra(Constants.KEY_USER_RATING,movieModel.getUser_rating());
        intent.putExtra(Constants.KEY_POSTER_URL,movieModel.getPosterUrl());
        intent.putExtra(Constants.KEY_MOVIE_ID,movieModel.getMovieId());
        startActivity(intent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        clearViews();
        fetchData(sharedPreferences.getString(getString(R.string.pref_sort_option_key),
                getString(R.string.pref_sort_option_popular_value)));

    }


    private class FetchDataAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... strings) {
            String response = null;
            try {
               response = NetworkUtils.getResponseFromUrl(buildUrl(strings[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            mProgressBar.setVisibility(View.GONE);
            super.onPostExecute(s);
            if(s!= null && !s.equals("")){
                try {
                    movieList = JsonUtils.getMovies(s);
                    showSData();
                } catch (JSONException e) {
                    e.printStackTrace();
                    showError();
                }

            }else {
                showError();
                Toast.makeText(MainActivity.this,getString(R.string.data_not_available), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showError(){
        tvErrorDisplay.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void showSData(){
        tvErrorDisplay.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        movieAdapter.setdata(movieList);
    }

    private void clearViews(){
        recyclerView.setVisibility(View.GONE);
        tvErrorDisplay.setVisibility(View.GONE);
    }

    private void fetchData(String path){
        new FetchDataAsyncTask().execute(path);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();
        switch (id){
            case R.id.action_settings:
                startActivity(new Intent(this,SettingsActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}
