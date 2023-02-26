package com.nameisjayant.noteappcompose.data.network

import com.nameisjayant.noteappcompose.data.model.Note
import com.nameisjayant.noteappcompose.data.model.NoteResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.*
import javax.inject.Inject

class ApiService @Inject constructor(
    private val httpClient: HttpClient
) {

    private val baseUrl = "http://18.235.233.99/api/todo"

    suspend fun getNotes(): List<NoteResponse> {
        return httpClient.get {
            url(baseUrl)
        }.body()
    }

    @OptIn(InternalAPI::class)
    suspend fun addNote(note: Note): NoteResponse {
        return httpClient.post {
            url(baseUrl)
            body = note
        }.body()
    }

    suspend fun deleteNote(id: Int): NoteResponse {
        return httpClient.delete {
            url("$baseUrl/$id/")
        }.body()
    }

    suspend fun updateNote(id: Int): NoteResponse {
        return httpClient.put {
            url("$baseUrl/$id")
        }.body()
    }

}