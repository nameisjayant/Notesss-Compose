package com.nameisjayant.noteappcompose.features.ui.screens

import LoadingBar
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nameisjayant.noteappcompose.R
import com.nameisjayant.noteappcompose.data.model.Note
import com.nameisjayant.noteappcompose.data.model.NoteResponse
import com.nameisjayant.noteappcompose.features.ui.viewmodel.NoteViewModel
import com.nameisjayant.noteappcompose.features.ui.viewmodel.events.NoteEvents
import com.nameisjayant.noteappcompose.features.ui.viewmodel.events.NoteUiEvents
import com.nameisjayant.noteappcompose.ui.theme.Background
import com.nameisjayant.noteappcompose.ui.theme.Red
import com.nameisjayant.noteappcompose.utils.showToast
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteScreen(
    viewModel: NoteViewModel = hiltViewModel()
) {
    var noteDialog by remember { mutableStateOf(false) }
    var updateDialog by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf("") }
    val response = viewModel.noteResponseEvent.value
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var noteId by remember { mutableStateOf(0) }


    if (isLoading) LoadingBar()

    // these launch effect are to handle the response of add , delete , update and show notes

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(NoteEvents.ShowNotes)
    }

    LaunchedEffect(key1 = true) {
        viewModel.updateNoteEvent.collectLatest {
            isLoading = when (it) {
                is NoteUiEvents.Success -> {
                    context.showToast("Note Updated!")
                    viewModel.onEvent(NoteEvents.ShowNotes)
                    updateDialog = false
                    false
                }

                is NoteUiEvents.Failure -> {
                    context.showToast(it.msg)
                    false
                }

                NoteUiEvents.Loading -> true

            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.deleteNoteEvent.collectLatest {
            isLoading = when (it) {
                is NoteUiEvents.Success -> {
                    context.showToast("Note Deleted")
                    viewModel.onEvent(NoteEvents.ShowNotes)
                    false
                }

                is NoteUiEvents.Failure -> {
                    context.showToast("Note Deleted")
                    viewModel.onEvent(NoteEvents.ShowNotes)
                    false
                }

                NoteUiEvents.Loading -> true

            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.addNoteEvent.collectLatest {
            isLoading = when (it) {
                is NoteUiEvents.Success -> {
                    title = ""
                    description = ""
                    context.showToast("Note Added")
                    noteDialog = false
                    viewModel.onEvent(NoteEvents.ShowNotes)
                    false
                }

                is NoteUiEvents.Failure -> {
                    Log.d("main", "NoteScreen:${it.msg} ")
                    context.showToast(it.msg)
                    false
                }

                NoteUiEvents.Loading -> true
            }
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            noteDialog = true
        }, backgroundColor = Red) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "", tint = Color.White)
        }
    }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            // search notes here

            AppSearchView(
                search = search,
                onValueChange = { search = it },
                modifier = Modifier.padding(start = 20.dp, top = 50.dp, end = 20.dp)
            )

            // it will show all notes
            if (response.data.isNotEmpty()) LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(20.dp),
            ) {
                val filterList: List<NoteResponse> = if (search.isEmpty()) {
                    response.data
                } else {
                    val result: ArrayList<NoteResponse> = arrayListOf()
                    for (temp in response.data) {
                        if (temp.title.lowercase(Locale.getDefault()).contains(
                                search.lowercase(
                                    Locale.getDefault()
                                )
                            ) || temp.description.lowercase(Locale.getDefault()).contains(
                                search.lowercase(Locale.getDefault())
                            )
                        ) {
                            result.add(temp)
                        }
                    }
                    result
                }

                items(filterList, key = { it.id }) {
                    NoteEachRow(note = it, {
                        // update api call
                        title = it.title
                        description = it.description
                        updateDialog = true
                        noteId = it.id
                    }) {
                        // delete api call
                        viewModel.onEvent(NoteEvents.DeleteNoteEvent(it.id))
                    }
                }
            }


            // loading while fetching all notes from server
            if (response.isLoading) Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Center
            ) {
                CircularProgressIndicator(color = Red)
            }


            // show error
            if (response.error.isNotEmpty()) Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Center
            ) {
                Text(text = response.error)
            }

        }


    }

    // open dialog to update notes

    if (updateDialog) AppAlertDialog(title, description, { title = it }, { description = it }, {
        updateDialog = it
    }) {
        if (title.isNotEmpty() && description.isNotEmpty()) {
            viewModel.onEvent(
                NoteEvents.UpdateNoteEvent(
                    id = noteId,
                    Note(title, description)
                )
            )
        } else {
            context.showToast(context.getString(R.string.add_title_and_description))
        }
    }


    // open dialog to add notes

    if (noteDialog)
        AppAlertDialog(
            title, description, { title = it },
            { description = it }, {
                noteDialog = it
            }
        ) {
            if (title.isNotEmpty() && description.isNotEmpty()) {
                viewModel.onEvent(
                    NoteEvents.AddNoteEvent(
                        Note(title, description)
                    )
                )
            } else {
                context.showToast(context.getString(R.string.add_title_and_description))
            }
        }

}
