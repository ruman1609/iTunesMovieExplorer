package com.rudyrachman16.itunesmovieexplorer.core.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rudyrachman16.itunesmovieexplorer.core.model.entity.MovieEntity

@Dao
interface FavoriteMovieDao {
    @Insert(entity = MovieEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    @Delete(entity = MovieEntity::class)
    suspend fun remove(movieEntity: MovieEntity)

    @Query("SELECT * FROM favoriteMovie")
    suspend fun getAllFav(): List<MovieEntity>

    @Query("SELECT COUNT(*) FROM favoriteMovie WHERE trackId=:trackId")
    fun countIsInFav(trackId: Long): Int
}