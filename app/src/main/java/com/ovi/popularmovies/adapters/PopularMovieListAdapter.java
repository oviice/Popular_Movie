package com.ovi.popularmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ovi.popularmovies.BuildConfig;
import com.ovi.popularmovies.R;
import com.ovi.popularmovies.interfaces.MovieIdInterface;
import com.ovi.popularmovies.modelClass.ResultsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularMovieListAdapter extends RecyclerView.Adapter<PopularMovieListAdapter.MyViewHolder> {

    Context context;
    List<ResultsItem> dataList;
    MovieIdInterface movieIdInterface;

    public PopularMovieListAdapter(Context context, List<ResultsItem> dataList, MovieIdInterface movieIdInterface) {
        this.context = context;
        this.dataList = dataList;
        this.movieIdInterface = movieIdInterface;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_movie, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (!dataList.get(position).getPosterPath().isEmpty()) {
            Picasso.get().load(BuildConfig.Image_BASE_URL + dataList.get(position).getPosterPath()).into(holder.movieImage);
        }
        holder.movieImage.setOnClickListener(v -> movieIdInterface.passMovie(String.valueOf(dataList.get(position).getId()), dataList.get(position).getTitle(),
                dataList.get(position).getPosterPath(), String.valueOf(dataList.get(position).getVoteAverage()),
                dataList.get(position).getReleaseDate(), dataList.get(position).getOverview()));

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.imageViewId);


        }
    }
}
