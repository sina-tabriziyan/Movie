package com.sina.movie.movieList.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.movie.movieList.data.local.movie.MovieDatabase
import com.sina.movie.movieList.data.remote.MovieApi
import com.sina.movie.movieList.data.remote.response.MovieDto
import com.sina.movie.movieList.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) {
    suspend fun movies(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<MovieDto>> = flow {
        emit(Resource.Loading)

        val movieByCategory = movieDatabase.movieDao.getMovieByCategory(category)
        val shouldLoadLocalMovie = movieByCategory.isEmpty() && !forceFetchFromRemote
        if (shouldLoadLocalMovie) {
            emit(Resource.Success(movieByCategory))
        }

        val response: Response<MovieDto> = movieApi.getMovies(category = category, page)
        val body = response.body()
        if (body != null) {
            when (response.code()) {
                200 -> emit(Resource.Error(response.message()))
                302 -> emit(Resource.Success(body))
                else -> emit(Resource.Error(response.message()))
            }
        } else {
            emit(Resource.Error("Response body is null"))
        }

    }.catch {
        emit(Resource.Error(it.message))
    }
}


class MovieViewModel @Inject constructor(
    private val repositoryImpl: MovieRepositoryImpl
) : ViewModel() {
    private val _movies = MutableLiveData<Resource<MovieDto>>()
    val movies: LiveData<Resource<MovieDto>> get() = _movies


    fun movies(category: String, page: Int) {
        viewModelScope.launch {
            val movies1 = repositoryImpl.movies(category, page)
        }
    }
}

