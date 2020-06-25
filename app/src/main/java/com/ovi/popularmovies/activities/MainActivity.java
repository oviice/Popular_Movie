package com.ovi.popularmovies.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ovi.popularmovies.BuildConfig;
import com.ovi.popularmovies.R;
import com.ovi.popularmovies.adapters.PopularMovieListAdapter;
import com.ovi.popularmovies.interfaces.MovieIdInterface;
import com.ovi.popularmovies.modelClass.PopularMovieListResponse;
import com.ovi.popularmovies.modelClass.ResultsItem;
import com.ovi.popularmovies.retrofit.AppConfig;
import com.ovi.popularmovies.retrofit.MoviesService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieIdInterface {

    @BindView(R.id.recyclerViewId)
    RecyclerView recyclerViewId;
    MoviesService moviesService;
    PopularMovieListAdapter popularMovieListAdapter;
    List<ResultsItem> movieList;

    int page = 1;
    MovieIdInterface movieIdInterface;
    boolean loading;
    Parcelable mListState;
    Bundle mBundleRecyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mBundleRecyclerViewState = new Bundle();
        movieIdInterface = this;
        movieList = new ArrayList<>();
        moviesService = AppConfig.getRetrofit(BuildConfig.BASE_URL).create(MoviesService.class);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerViewId.setLayoutManager(gridLayoutManager);

        popularMovieListAdapter = new PopularMovieListAdapter(MainActivity.this, movieList, movieIdInterface);
        recyclerViewId.setAdapter(popularMovieListAdapter);

        getPopularMoviesData();

        recyclerViewId.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int visibleItemCount = gridLayoutManager.getChildCount();
                    int totalItemCount = gridLayoutManager.getItemCount();
                    int pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = true;
                            page++;
                            getPopularMoviesData();
                        }
                    }
                }
            }
        });

    }

    private void getPopularMoviesData() {
        moviesService.getPopularMovies(BuildConfig.API_KEY, "en-US", String.valueOf(page)).enqueue(new Callback<PopularMovieListResponse>() {
            @Override
            public void onResponse(Call<PopularMovieListResponse> call, Response<PopularMovieListResponse> response) {
                try {
                    movieList.addAll(response.body().getResults());
                    popularMovieListAdapter.notifyDataSetChanged();
                    loading = false;
                } catch (Exception e) {
                    Log.d("ovias", "onResponse: " + e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<PopularMovieListResponse> call, Throwable t) {
                Log.d("ovias", "onFailure: " + t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void passMovie(String id, String movieName, String imageUrl, String rating, String releaseYear, String overview) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("Movie_id", id);
        intent.putExtra("Movie_Name", movieName);
        intent.putExtra("Movie_Image_Url", imageUrl);
        intent.putExtra("Movie_rating", rating);
        intent.putExtra("Movie_release_year", releaseYear);
        intent.putExtra("Overview", overview);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mListState = recyclerViewId.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable("KEY_RECYCLER_STATE", mListState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (recyclerViewId != null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mListState = mBundleRecyclerViewState.getParcelable("KEY_RECYCLER_STATE");
                    recyclerViewId.getLayoutManager().onRestoreInstanceState(mListState);

                }
            }, 50);

        }
        Log.d("ASadasd", "onConfigurationChanged: ");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerViewId.setLayoutManager(gridLayoutManager);


    }

}