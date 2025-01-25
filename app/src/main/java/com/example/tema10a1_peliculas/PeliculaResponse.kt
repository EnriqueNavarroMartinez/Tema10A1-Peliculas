package com.example.tema10a1_peliculas

data class PeliculaResponse(
    val count: Int,
    val pokemon: List<Pelicula>
)

data class Pelicula(
    val Title: String,
    val Year: String,
    val Director: String,
    val Poster: String
)

