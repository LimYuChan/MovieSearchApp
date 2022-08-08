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

/**
 * Retrofit DI 관련 코드입니다.
 *
 * Logger interceptor = 빌드 환경에 따라 로그를 출력합니다.
 *
 * Exception interceptor = 네이버 API 사용 중 발생할 수 있는 Http 응답 코드가 들어오면 Exception을 발생시킵니다.
 * 발생된 Exception은 PagingSource에서 케치하여 핸들링 하도록 구성하였습니다.
 * todo 실제 프로젝트에서는 에러마다 동작하는 기획에 따라 달라질 수 있기 때문에 exception을 나눴습니다.
 *
 * Auth header interceptor = Naver API를 사용하는데 필요한 키들을 헤더에 추가합니다.
 */

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