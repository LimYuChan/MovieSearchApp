package com.devsurfer.data.di

import com.devsurfer.data.BuildConfig
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
    }

    private val headerInterceptor = Interceptor{ chain ->
        chain.proceed(chain.request())
            .newBuilder()
            .addHeader("X-Naver-Client-Id", BuildConfig.CLIENT_ID)
            .addHeader("X-Naver-Client-Secret", BuildConfig.CLIENT_SECRET)
            .build()
    }

    private val gson = GsonBuilder().setLenient().create()

    private const val SEARCH_URL = "https://openapi.naver.com/v1/search/"

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(SEARCH_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
}