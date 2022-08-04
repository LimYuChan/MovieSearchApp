package com.devsurfer.data.di

import android.util.Log
import com.devsurfer.data.BuildConfig
import com.devsurfer.data.exception.NetworkException
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

    private val headerInterceptor = Interceptor{ chain ->
        chain.proceed(chain.request())
            .newBuilder()
            .addHeader("X-Naver-Client-Id", BuildConfig.CLIENT_ID)
            .addHeader("X-Naver-Client-Secret", BuildConfig.CLIENT_SECRET)
            .build()
    }

    private val exceptionInterceptor = Interceptor{chain ->
        try{
            val response = chain.proceed(chain.request())
           when(response.code){
               Constants.CODE_BAD_REQUEST -> throw NetworkException.BadRequestException()
               Constants.CODE_NOT_AVAILABLE_ACCESS_KEY -> throw  NetworkException.NotAvailableAccessKeyException()
               else-> response
           }
        }catch (e: IOException){
            throw NetworkException.NetworkNotConnectedException()
        }catch (t: Throwable){
            Log.d("interceptor", "exception interceptor: ${t.message}")
            throw NetworkException.UnHandleException()
        }
    }

    private val gson = GsonBuilder().setLenient().create()

    private const val SEARCH_URL = "https://openapi.naver.com/v1/search/"

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
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