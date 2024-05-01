package com.rudyrachman16.itunesmovieexplorer.core.api

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale

interface ApiService {
    @GET("search?media=movie")
    suspend fun getMoviesByKeyword(
        @Query("term") keyword: String,
        @Query("country") countryCode: String = Locale.getDefault().country
    ): JsonObject
}