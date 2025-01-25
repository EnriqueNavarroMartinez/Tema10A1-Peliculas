package com.example.tema10a1_peliculas

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PeliculaApiService {
    @GET("/")
    fun getPelicula(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String
    ): Call<Pelicula>
}