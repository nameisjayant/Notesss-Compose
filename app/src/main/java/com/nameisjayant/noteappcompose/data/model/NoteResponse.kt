package com.nameisjayant.noteappcompose.data.model

import kotlinx.serialization.Serializable
//@Serializable
data class NoteResponse(
    val id: Int,
    val title: String,
    val description: String,
    val created_at: String,
    val updated_at:String
)

//@Serializable
data class Note(
    val title:String,
    val description: String,
)