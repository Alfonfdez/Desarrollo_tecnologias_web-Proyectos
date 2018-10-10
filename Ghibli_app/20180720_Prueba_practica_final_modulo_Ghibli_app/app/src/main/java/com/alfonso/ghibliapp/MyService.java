package com.alfonso.ghibliapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyService {

    @GET("films/")
    Call<List<Film>> listFilms();

    @GET("films/{film_id}")
    Call<Film> getFilm(@Path("film_id") String film_id);

  /*  @DELETE("films/{film_id}")
    Call<Void> deleteFilm(@Path("film_id") String film_id);

    @POST("films/")
    Call<Film> saveFilm(@Body Film film);*/

}
