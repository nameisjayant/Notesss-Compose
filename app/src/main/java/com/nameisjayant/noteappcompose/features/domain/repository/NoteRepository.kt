package com.nameisjayant.noteappcompose.features.domain.repository

import com.nameisjayant.noteappcompose.data.model.Note
import com.nameisjayant.noteappcompose.data.model.NoteResponse
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun addNote(note: Note): Flow<NoteResponse>

    suspend fun getNotes(): Flow<List<NoteResponse>>

    suspend fun deleteNote(id: Int): Flow<NoteResponse>

    suspend fun updateNote(id: Int,note: Note): Flow<NoteResponse>

}