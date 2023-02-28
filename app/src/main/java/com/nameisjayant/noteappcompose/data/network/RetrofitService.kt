package com.nameisjayant.noteappcompose.data.network

import com.nameisjayant.noteappcompose.data.model.Note
import com.nameisjayant.noteappcompose.data.model.NoteResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// api calling for retrofit
interface RetrofitService {

    companion object {
         const val baseUrl = "http://18.235.233.99/api/"
    }

    @GET("todo")
    suspend fun getNotes(): List<NoteResponse>

    @POST("todo/")
    suspend fun addNotes(
        @Body note: Note
    ): NoteResponse

    @PUT("todo/{id}/")
    suspend fun updateNote(
        @Path("id") id: Int,
        @Body note: Note
    ): NoteResponse

    @DELETE("todo/{id}/")
    suspend fun deleteNote(
        @Path("id") id: Int
    ): NoteResponse

}