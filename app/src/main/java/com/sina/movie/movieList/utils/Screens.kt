package com.sina.movie.movieList.utils

sealed class Screens(val roust: String) {
    data object Home : Screens("main")
    data object PopularMovieList : Screens("popularMovies")
    data object UpCompingMovies : Screens("upcomingMovies")
    data object Details : Screens("details")

}