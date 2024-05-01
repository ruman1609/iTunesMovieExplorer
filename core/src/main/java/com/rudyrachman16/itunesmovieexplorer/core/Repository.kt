package com.rudyrachman16.itunesmovieexplorer.core

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rudyrachman16.itunesmovieexplorer.core.api.ApiService
import com.rudyrachman16.itunesmovieexplorer.core.model.Movie
import com.rudyrachman16.itunesmovieexplorer.core.model.response.MovieResponse
import com.rudyrachman16.itunesmovieexplorer.core.room.FavoriteMovieDao
import com.rudyrachman16.itunesmovieexplorer.core.utils.FlowUtils
import com.rudyrachman16.itunesmovieexplorer.core.utils.MappingUtils.toMovie
import com.rudyrachman16.itunesmovieexplorer.core.utils.MappingUtils.toMovieEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService,
    private val dbDao: FavoriteMovieDao
) {
    fun getMovies(keyword: String) = FlowUtils.defaultFlowCallback {
        val result = apiService.getMoviesByKeyword(keyword)
        val list = result.getAsJsonArray("results")
        Gson().fromJson<List<MovieResponse>>(
            list.toString(),
            TypeToken.getParameterized(List::class.java, MovieResponse::class.java).type
        ).map {
            it.toMovie().apply { isFavorite = dbDao.countIsInFav(trackId) >= 1 }
        }
    }

    fun getFavoriteMovies() = FlowUtils.defaultFlowCallback {
        val result = dbDao.getAllFav()
        result.map { it.toMovie() }
    }

    fun insertToFavorite(movie: Movie) = FlowUtils.defaultFlowCallback {
        dbDao.insert(movie.toMovieEntity())
        context.getString(
            R.string.success_message, context.getString(R.string.add_to_fav, movie.trackName)
        )
    }

    fun deleteFromFavorite(movie: Movie) = FlowUtils.defaultFlowCallback {
        dbDao.remove(movie.toMovieEntity())
        context.getString(
            R.string.success_message, context.getString(R.string.remove_from_fav, movie.trackName)
        )
    }

    fun checkIsInFav(movie: Movie): Boolean = dbDao.countIsInFav(movie.trackId) >= 1
}