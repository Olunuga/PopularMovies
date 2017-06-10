package com.myorh.popularmovies;

import android.content.Context;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.myorh.popularmovies.Utils.Constants;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView tvTitle,tvUser_rating,tvRelease_date,tvPlot;
    ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
        actionBar.setHomeButtonEnabled(true);
        }

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvUser_rating = (TextView) findViewById(R.id.tv_user_rating);
        tvRelease_date = (TextView) findViewById(R.id.tv_release_date);
        tvPlot = (TextView) findViewById(R.id.tv_plot);
        poster = (ImageView) findViewById(R.id.img_detail_poster);

        if(getIntent().hasExtra(Constants.KEY_MOVIE_ID)){
            tvTitle.setText(getIntent().getStringExtra(Constants.KEY_TITLE));
            tvUser_rating.setText(String.valueOf(getIntent().getDoubleExtra(Constants.KEY_USER_RATING,0.0)));
            tvRelease_date.setText(getIntent().getStringExtra(Constants.KEY_RELEASE_DATE));
            tvPlot.setText(getIntent().getStringExtra(Constants.KEY_PLOT));
            String url = getIntent().getStringExtra(Constants.KEY_POSTER_URL);
            Picasso.with(this).load(Constants.IMAGE_DETAIL_BASE_URL+url)
                    .placeholder(this.getResources().getDrawable(R.drawable.placeholder
                    )).into(poster);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
