package com.nameisjayant.noteappcompose.data.repository

import com.nameisjayant.noteappcompose.data.model.Note
import com.nameisjayant.noteappcompose.data.model.NoteResponse
import com.nameisjayant.noteappcompose.data.network.KtorService
import com.nameisjayant.noteappcompose.data.network.RetrofitService
import com.nameisjayant.noteappcompose.features.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val apiService: KtorService,
    private val retrofitService: RetrofitService
) : NoteRepository {


    override suspend fun addNote(note: Note): Flow<NoteResponse> = flow {
        emit(retrofitService.addNotes(note))
    }.flowOn(Dispatchers.IO)

    override suspend fun getNotes(): Flow<List<NoteResponse>> = flow {
        emit(retrofitService.getNotes())
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteNote(id: Int): Flow<NoteResponse> = flow {
        emit(retrofitService.deleteNote(id))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateNote(id: Int, note: Note): Flow<NoteResponse> = flow {
        emit(retrofitService.updateNote(id, note))
    }.flowOn(Dispatchers.IO)


}