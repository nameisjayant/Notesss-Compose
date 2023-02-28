package com.nameisjayant.noteappcompose.di

import android.util.Log
import com.nameisjayant.noteappcompose.data.network.RetrofitService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.gson.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.serialization.gson.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // ktor dependency injection
    @Provides
    @Singleton
    fun createJson(): Json = Json {
        isLenient = true; ignoreUnknownKeys = true;
        encodeDefaults = true; prettyPrint = true
    }


    @Provides
    @Singleton
    fun providesHttpClient(
        json: Json
    ): HttpClient = HttpClient(Android) {

        install(DefaultRequest) {
            headers.append("Content-Type", "application/json")
        }

        install(ContentNegotiation) {
            json(json)
        }


        install(Logging) {
            logger = CustomHttpLogger()
            level = LogLevel.INFO
        }

        install(ResponseObserver) {
            onResponse {
                Log.d("log", "${it.status.value}")
            }
        }
    }

    // retrofit dependencies injection

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi
        .Builder()
        .run {
            add(KotlinJsonAdapterFactory())
            build()
        }

    @Provides
    @Singleton
    fun providesRetrofitService(moshi: Moshi,client: OkHttpClient): RetrofitService = Retrofit
        .Builder()
        .run {
            baseUrl(RetrofitService.baseUrl)
            addConverterFactory(MoshiConverterFactory.create(moshi))
            client(client)
            build()
        }.create(RetrofitService::class.java)

    @Provides
    @Singleton
    fun providesOkHttp(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(logger)
            .build()
    }

}

class CustomHttpLogger : Logger {
    override fun log(message: String) {
        Log.d("log", message)
    }
}