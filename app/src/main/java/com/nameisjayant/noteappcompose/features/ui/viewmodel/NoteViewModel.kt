package com.nameisjayant.noteappcompose.features.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nameisjayant.noteappcompose.features.domain.repository.NoteRepository
import com.nameisjayant.noteappcompose.features.ui.viewmodel.events.NoteEvents
import com.nameisjayant.noteappcompose.features.ui.viewmodel.events.NoteUiEvents
import com.nameisjayant.noteappcompose.features.ui.viewmodel.states.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _noteResponseEvent = mutableStateOf(NoteState())
    val noteResponseEvent: State<NoteState> = _noteResponseEvent

    private val _addNoteEvent: MutableSharedFlow<NoteUiEvents> = MutableSharedFlow()
    val addNoteEvent = _addNoteEvent.asSharedFlow()

    private val _deleteNoteEvent: MutableSharedFlow<NoteUiEvents> = MutableSharedFlow()
    val deleteNoteEvent = _deleteNoteEvent.asSharedFlow()

    private val _updateNoteEvent: MutableSharedFlow<NoteUiEvents> = MutableSharedFlow()
    val updateNoteEvent = _updateNoteEvent.asSharedFlow()


    init {
        viewModelScope.launch {
            repository.getNotes()
                .onStart {
                    _noteResponseEvent.value = NoteState(
                        isLoading = true
                    )
                }.catch {
                    Log.d("main hey12", "${it.message}")
                    _noteResponseEvent.value = NoteState(
                        error = it.message ?: "Something went wrong"
                    )
                }.collect {
                    Log.d("main hey", "$it")
                    _noteResponseEvent.value = NoteState(
                        data = it
                    )
                }
        }
    }

    fun onEvent(events: NoteEvents) {
        when (events) {
            is NoteEvents.AddNoteEvent -> {
                viewModelScope.launch {
                    repository.addNote(events.data)
                        .onStart {
                            _addNoteEvent.emit(
                                NoteUiEvents.Loading
                            )
                        }.catch {
                            _addNoteEvent.emit(
                                NoteUiEvents.Failure(it.message ?: "Something went wrong")
                            )
                        }.collect {
                            _addNoteEvent.emit(
                                NoteUiEvents.Success(it)
                            )
                        }
                }
            }
            is NoteEvents.DeleteNoteEvent -> {
                viewModelScope.launch {
                    repository.deleteNote(events.id)
                        .onStart {
                            _deleteNoteEvent.emit(
                                NoteUiEvents.Loading
                            )
                        }.catch {
                            _deleteNoteEvent.emit(
                                NoteUiEvents.Failure(it.message ?: "Something went wrong")
                            )
                        }.collect {
                            _deleteNoteEvent.emit(
                                NoteUiEvents.Success(it)
                            )
                        }
                }
            }
            is NoteEvents.UpdateNoteEvent -> {
                viewModelScope.launch {
                    repository.updateNote(events.id)
                        .onStart {
                            _updateNoteEvent.emit(
                                NoteUiEvents.Loading
                            )
                        }.catch {
                            _updateNoteEvent.emit(
                                NoteUiEvents.Failure(it.message ?: "Something went wrong")
                            )
                        }.collect {
                            _updateNoteEvent.emit(
                                NoteUiEvents.Success(it)
                            )
                        }
                }
            }
        }
    }


}