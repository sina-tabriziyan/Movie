package com.sina.movie.movieList.domain

import com.sina.movie.movieList.data.remote.response.MovieDto
import com.sina.movie.movieList.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun movies(
        forceFetchFromRemote:Boolean,
        category: String,
        page: Int
    ): Flow<Resource<MovieDto>>
}