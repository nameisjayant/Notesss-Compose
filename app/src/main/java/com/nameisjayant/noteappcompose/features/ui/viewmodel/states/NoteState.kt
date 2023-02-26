package com.nameisjayant.noteappcompose.features.ui.viewmodel.states

import com.nameisjayant.noteappcompose.data.model.NoteResponse


data class NoteState(
    val data:List<NoteResponse> = emptyList(),
    val error:String = "",
    val isLoading:Boolean = false
)