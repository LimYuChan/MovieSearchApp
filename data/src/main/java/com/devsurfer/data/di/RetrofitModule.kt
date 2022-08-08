package com.devsurfer.data.di

import com.devsurfer.data.BuildConfig
import com.devsurfer.domain.exception.NetworkException
import com.devsurfer.domain.utils.Constants
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

    private val exceptionInterceptor = Interceptor{chain ->
        val response = chain.proceed(chain.request())
        when(response.code){
            Constants.CODE_INCORRECT_QUERY -> throw NetworkException.IncorrectQuery()
            Constants.CODE_INVALID_SEARCH_API -> throw  NetworkException.InvalidSearchApi()
            Constants.CODE_SEARCH_SYSTEM_ERROR -> throw NetworkException.SearchSystemError()
            else-> response
        }
    }

    private val authHeaderInterceptor = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Naver-Client-Id", BuildConfig.CLIENT_ID)
            .addHeader("X-Naver-Client-Secret", BuildConfig.CLIENT_SECRET)
            .build()
        chain.proceed(request)
    }

    private val gson = GsonBuilder().setLenient().create()

    private const val SEARCH_URL = "https://openapi.naver.com/v1/search/"


    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(exceptionInterceptor)
            .addInterceptor(authHeaderInterceptor)
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