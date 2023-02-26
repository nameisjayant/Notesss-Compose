package com.nameisjayant.noteappcompose.di

import android.util.Log
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
import io.ktor.serialization.gson.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun createJson(): Json = Json {
       isLenient = true; ignoreUnknownKeys = true;
        encodeDefaults = true }


    @Provides
    @Singleton
    fun providesHttpClient(
        json: Json
    ):HttpClient = HttpClient{

        install(ContentNegotiation){
            gson()
        }

        install(DefaultRequest) {
            headers.append("Content-Type", "application/json")
        }

        install(Logging) {
            logger = CustomHttpLogger()
            level = LogLevel.INFO
        }
    }

}

class CustomHttpLogger: Logger {
    override fun log(message: String) {
        Log.d("log", message)
    }
}