package com.mvvmdemo.di

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mvvmdemo.network.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    // Dependency: HttpLoggingInterceptor
    single<Interceptor> {
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.e("OkHttp",""+message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // Dependency: OkHttpClient
    single {
        OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(get<Interceptor>())
                .build()
    }

    // Dependency: Retrofit
    single {
        Retrofit.Builder()
                .baseUrl("https://staging.sunteccity.com.sg/ver16/index.php/")
                .client(get())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    // Dependency: ApiService
    single {
        val retrofit: Retrofit = get()
        retrofit.create(ApiService::class.java)
    }

}