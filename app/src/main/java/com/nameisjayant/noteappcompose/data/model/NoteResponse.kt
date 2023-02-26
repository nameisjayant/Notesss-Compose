package com.nameisjayant.noteappcompose.data.model

@kotlinx.serialization.Serializable
data class NoteResponse(
    val id: Int,
    val title: String,
    val description: String,
    val created_at: String,
    val updated_at:String
)

@kotlinx.serialization.Serializable
data class Note(
    val title:String,
    val description: String,
)