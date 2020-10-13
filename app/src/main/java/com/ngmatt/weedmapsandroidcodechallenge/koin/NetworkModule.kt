package com.ngmatt.weedmapsandroidcodechallenge.koin

import android.content.Context
import com.ngmatt.weedmapsandroidcodechallenge.R
import com.ngmatt.weedmapsandroidcodechallenge.data.FusionApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

val networkModule =  module {
    factory { provideMoshi() }
    factory { provideCache(androidContext()) }
    factory { RequestInterceptor() }
    factory { FusionApiInterceptor(androidContext()) }
    factory { provideOkHttpClient(get(), get(), get()) }
    factory { provideFusionApi(get()) }
    single { provideRetrofit(androidContext(), get(), get()) }
}

fun provideRetrofit(context: Context, moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(context.getString(R.string.api_url))
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi)
            .asLenient()
        )
        .build()
}

fun provideCache(context: Context) : Cache {
    val cacheSize: Long = 1024 * 1024 * 20 // 20 MB
    return Cache(context.cacheDir, cacheSize)
}

fun provideMoshi(): Moshi {
    return Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

fun provideOkHttpClient(fusionApiInterceptor: FusionApiInterceptor, cacheInterceptor: RequestInterceptor, cache: Cache): OkHttpClient {
    return OkHttpClient().newBuilder()
        .cache(cache)
        .addNetworkInterceptor(cacheInterceptor)
        .addInterceptor(fusionApiInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()
}

fun provideFusionApi(retrofit: Retrofit): FusionApi {
    return retrofit.create(FusionApi::class.java)
}

class FusionApiInterceptor(private val context: Context): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        val newHeaders = req.headers().newBuilder()
            .add("Authorization", "Bearer ${context.getString(R.string.fusion_api_key)}")
            .build()
        req = req.newBuilder().headers(newHeaders).build()
        return chain.proceed(req)
    }

}

class RequestInterceptor: Interceptor {
    companion object {
        private const val CACHE_AGE_DAYS = 15 * 60 * 60 * 24 // 15 days
    }

    override fun intercept(chain: Interceptor.Chain) : Response {
        val originalResponse = chain.proceed(chain.request())

        return originalResponse.newBuilder()
            .header("Cache-Control", "public, max-age=$CACHE_AGE_DAYS")
            .build()
    }
}