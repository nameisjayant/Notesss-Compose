package com.nameisjayant.noteappcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun createJson(): Json = Json { isLenient = true; ignoreUnknownKeys = true }


    @Provides
    @Singleton
    fun providesHttpClient(
        json: Json
    ):HttpClient = HttpClient(Android){

        install(ContentNegotiation){
            json(json)
        }

        install(DefaultRequest) {
            headers.append("Content-Type", "application/json")
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }

}