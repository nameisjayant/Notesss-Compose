package com.nameisjayant.noteappcompose.data.model

data class NoteResponse(
    val id: Int,
    val title: String,
    val description: String,
    val created_at: String,
    val updated_at: String
)

data class Note(
    val title: String,
    val description: String,
)