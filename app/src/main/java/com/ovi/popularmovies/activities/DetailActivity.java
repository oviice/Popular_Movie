package com.ovi.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ovi.popularmovies.BuildConfig;
import com.ovi.popularmovies.R;
import com.ovi.popularmovies.adapters.TrailerListAdapter;
import com.ovi.popularmovies.interfaces.TrailerInterface;
import com.ovi.popularmovies.modelClass.detailModel.DetailResponse;
import com.ovi.popularmovies.modelClass.detailModel.ResultsItem;
import com.ovi.popularmovies.retrofit.AppConfig;
import com.ovi.popularmovies.retrofit.MoviesService;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements TrailerInterface {
    MoviesService moviesService;
    @BindView(R.id.movieName)
    TextView movieName;
    @BindView(R.id.movieImage)
    ImageView movieImage;
    @BindView(R.id.releaseYear)
    TextView releaseYear;
    @BindView(R.id.rating)
    TextView rating;
    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.trailerRecyclerView)
    RecyclerView trailerRecyclerView;

    String movie_id, movieNameString, movieImageUrl, movieRating, movieReleaseYear, overviewString;
    TrailerInterface trailerInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        trailerInterface = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Intent intent = getIntent();
        try {
            movie_id = intent.getStringExtra("Movie_id");
            movieNameString = intent.getStringExtra("Movie_Name");
            movieImageUrl = intent.getStringExtra("Movie_Image_Url");
            movieRating = intent.getStringExtra("Movie_rating");
            movieReleaseYear = intent.getStringExtra("Movie_release_year");
            overviewString = intent.getStringExtra("Overview");
        } catch (Exception e) {
        }
        moviesService = AppConfig.getRetrofit(BuildConfig.BASE_URL + "movie/" + movie_id + "/").create(MoviesService.class);
        trailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getMovieDetails();
        setData();
    }

    private void setData() {
        try {
            movieName.setText(movieNameString);
            if (!movieImageUrl.isEmpty()) {
                Picasso.get().load(BuildConfig.Image_BASE_URL + movieImageUrl).into(movieImage);
            }
            rating.setText(movieRating + "/10");
            String[] year = movieReleaseYear.split("-");
            releaseYear.setText(year[0]);
            overview.setText(overviewString);
        } catch (Exception ignored) {
        }
    }

    private void getMovieDetails() {
        moviesService.getDetailMovies(BuildConfig.API_KEY).enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                try {
                    Log.d("asfdasf", "onResponse: " + new Gson().toJson(response.body()));
                    List<ResultsItem> list = response.body().getResults();
                    trailerRecyclerView.setAdapter(new TrailerListAdapter(DetailActivity.this, list, trailerInterface));
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.favouriteBtn)
    public void onViewClicked() {
        Toast.makeText(this, "Favourite", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void passTrailerKey(String key) {
        Log.d("sadsda", "passTrailerKey: " + key);
        Intent intent = new Intent(DetailActivity.this, PlayerActivity.class);
        intent.putExtra("VIDEOID", key);
        startActivity(intent);
    }
}