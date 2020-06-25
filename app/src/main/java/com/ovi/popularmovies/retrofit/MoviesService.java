package com.ovi.popularmovies.retrofit;


import com.ovi.popularmovies.adapters.PopularMovieListAdapter;
import com.ovi.popularmovies.modelClass.PopularMovieListResponse;
import com.ovi.popularmovies.modelClass.detailModel.DetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesService {
    @GET("movie/popular")
    Call<PopularMovieListResponse> getPopularMovies(@Query("api_key") String api_key, @Query("language") String language,
                                                    @Query("page") String page);

    @GET("videos")
    Call<DetailResponse> getDetailMovies(@Query("api_key") String api_key);

}
