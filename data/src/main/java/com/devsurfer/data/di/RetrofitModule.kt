package com.devsurfer.data.di

import android.content.Context
import android.util.Log
import com.devsurfer.data.BuildConfig
import com.devsurfer.domain.exception.NetworkException
import com.devsurfer.data.interceptor.CacheSettingInterceptor
import com.devsurfer.domain.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
    }

    private val exceptionInterceptor = Interceptor{chain ->
        val response = chain.proceed(chain.request())
        when(response.code){
            Constants.CODE_INCORRECT_QUERY -> throw NetworkException.IncorrectQueryException()
            Constants.CODE_INVALID_SEARCH_API -> throw  NetworkException.InvalidSearchApiException()
            Constants.CODE_SEARCH_SYSTEM_ERROR -> throw NetworkException.SearchSystemErrorException()
            else-> response
        }
    }

    private val gson = GsonBuilder().setLenient().create()

    private const val SEARCH_URL = "https://openapi.naver.com/v1/search/"

    @Singleton
    @Provides
    fun provideCacheSettingInterceptor(@ApplicationContext context: Context): CacheSettingInterceptor = CacheSettingInterceptor(context)

    @Singleton
    @Provides
    fun provideOkHttp(cacheSettingInterceptor: CacheSettingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .cache(cacheSettingInterceptor.myCache)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(cacheSettingInterceptor)
            .addInterceptor(exceptionInterceptor)
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