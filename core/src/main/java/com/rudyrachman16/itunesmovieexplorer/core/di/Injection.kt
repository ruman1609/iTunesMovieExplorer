package com.rudyrachman16.itunesmovieexplorer.core.di

import android.content.Context
import androidx.room.Room
import com.rudyrachman16.itunesmovieexplorer.core.BuildConfig
import com.rudyrachman16.itunesmovieexplorer.core.api.ApiService
import com.rudyrachman16.itunesmovieexplorer.core.room.FavoriteMovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Injection {
    @Singleton
    @Provides
    fun provideInterceptorLogging(): HttpLoggingInterceptor =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    @Singleton
    @Provides
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, factory: GsonConverterFactory): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(client).addConverterFactory(factory)
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideFavoriteMovieDao(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, FavoriteMovieDatabase::class.java, "iTunesMovieExplorerDB")
            .fallbackToDestructiveMigration().build().getFavoriteMovieDao()
}