package com.rudyrachman16.itunesmovieexplorer.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rudyrachman16.itunesmovieexplorer.core.model.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class FavoriteMovieDatabase: RoomDatabase() {
    abstract fun getFavoriteMovieDao() : FavoriteMovieDao
}