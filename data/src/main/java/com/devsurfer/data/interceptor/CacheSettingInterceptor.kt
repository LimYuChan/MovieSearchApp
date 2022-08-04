package com.devsurfer.data.interceptor

import android.content.Context
import android.net.ConnectivityManager
import com.devsurfer.data.BuildConfig
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CacheSettingInterceptor @Inject constructor(
    private val context: Context
): Interceptor{

    private val cacheSize = (5 * 1024 * 1024).toLong()
    val myCache = Cache(context.cacheDir, cacheSize)

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if(request.header("Cache-control") == null){
            request = if(isConnected()){
                request.newBuilder()
                    .addHeader("Cache-control", "public, max-age=5")
                    .addHeader("X-Naver-Client-Id", BuildConfig.CLIENT_ID)
                    .addHeader("X-Naver-Client-Secret", BuildConfig.CLIENT_SECRET)
                    .build()
            }else{
                request.newBuilder()
                    .addHeader("Cache-control", "public, only-if-cached, max-stale=${60 * 60 * 24 * 7}")
                    .addHeader("X-Naver-Client-Id", BuildConfig.CLIENT_ID)
                    .addHeader("X-Naver-Client-Secret", BuildConfig.CLIENT_SECRET)
                    .build()
            }
        }
        return chain.proceed(request)
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.isDefaultNetworkActive
    }
}