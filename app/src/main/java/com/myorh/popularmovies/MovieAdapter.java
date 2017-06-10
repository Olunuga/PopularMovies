package com.myorh.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.myorh.popularmovies.Data.MovieModel;
import com.myorh.popularmovies.Utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Myorh on 6/7/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieListViewHolder> {
    List<MovieModel> mMovieList;
    Context mContext;
    MovieClickListener listener;

    public MovieAdapter(Context context) {
        mContext = context;
    }


    public interface MovieClickListener{
        void OnMovieClick(MovieModel movieModel);
    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layout = R.layout.single_movie;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layout, parent, shouldAttachToParentImmediately);
        return new MovieListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, int position) {
        holder.bindView(position);

    }

    public void setdata(List<MovieModel> movieList){
        mMovieList = movieList;
        notifyDataSetChanged();
    }

    public void setListener(MovieClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mMovieList == null?0:mMovieList.size();
    }

    public class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView poster;

        public MovieListViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.img_movie_poster);
            poster.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if(listener != null){
                listener.OnMovieClick(mMovieList.get(getLayoutPosition()));
            }
        }

        public void bindView(int position){
            Picasso picasso = Picasso.with(mContext);
            picasso.setIndicatorsEnabled(false);
            picasso.load(Constants.IMAGE_DETAIL_BASE_URL+mMovieList
                    .get(position).getPosterUrl())
                    .placeholder(mContext.getResources().getDrawable(R.drawable.placeholder
                    )).into(poster);

        }
    }
}
