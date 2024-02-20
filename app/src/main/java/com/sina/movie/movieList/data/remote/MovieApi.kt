package com.sina.movie.movieList.data.remote

import com.sina.movie.movieList.data.local.movie.MovieDao
import com.sina.movie.movieList.data.remote.response.MovieDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apikey: String = API_KEY,
    ):Response<MovieDto>


    companion object {
        const val BASE_URL = "https://api.themoviesdb.org/3/"
        const val IMAGE_BASE_URl = "https://image.tmdb.org/t/p/w500/"
        const val API_KEY = "8fe016c4af895cab2fdfa8c645487e63"
    }
}