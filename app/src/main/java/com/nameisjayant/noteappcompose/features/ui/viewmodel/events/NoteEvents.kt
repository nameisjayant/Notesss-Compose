package com.nameisjayant.noteappcompose.features.ui.viewmodel.events

import com.nameisjayant.noteappcompose.data.model.Note
import com.nameisjayant.noteappcompose.data.model.NoteResponse

sealed class NoteEvents {
    data class AddNoteEvent(val data: Note) : NoteEvents()
    data class DeleteNoteEvent(val id: Int) : NoteEvents()
    data class UpdateNoteEvent(val id: Int, val note: Note) : NoteEvents()
    object ShowNotes : NoteEvents()

}

sealed class NoteUiEvents {
    data class Success(val data: NoteResponse) : NoteUiEvents()
    data class Failure(val msg: String) : NoteUiEvents()
    object Loading : NoteUiEvents()

}