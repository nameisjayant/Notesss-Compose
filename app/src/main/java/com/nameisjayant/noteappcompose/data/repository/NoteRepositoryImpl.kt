package com.nameisjayant.noteappcompose.data.repository

import com.nameisjayant.noteappcompose.data.model.Note
import com.nameisjayant.noteappcompose.data.model.NoteResponse
import com.nameisjayant.noteappcompose.data.network.ApiService
import com.nameisjayant.noteappcompose.features.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : NoteRepository{


    override suspend fun addNote(note: Note): Flow<NoteResponse> = flow{
        emit(apiService.addNote(note))
    }.flowOn(Dispatchers.IO)

    override suspend fun getNotes(): Flow<List<NoteResponse>> = flow{
        emit(apiService.getNotes())
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteNote(id: Int): Flow<NoteResponse> = flow{
        emit(apiService.deleteNote(id))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateNote(id: Int): Flow<NoteResponse> = flow{
       emit(apiService.updateNote(id))
    }.flowOn(Dispatchers.IO)


}