package com.nameisjayant.noteappcompose.features.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.nameisjayant.noteappcompose.R
import com.nameisjayant.noteappcompose.data.model.NoteResponse
import com.nameisjayant.noteappcompose.features.ui.viewmodel.NoteViewModel
import com.nameisjayant.noteappcompose.features.ui.viewmodel.events.NoteEvents
import com.nameisjayant.noteappcompose.features.ui.viewmodel.events.NoteUiEvents
import com.nameisjayant.noteappcompose.ui.theme.Background
import com.nameisjayant.noteappcompose.ui.theme.ContentColor
import com.nameisjayant.noteappcompose.ui.theme.Red
import com.nameisjayant.noteappcompose.utils.showToast
import kotlinx.coroutines.flow.collectLatest
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteScreen(
    viewModel: NoteViewModel = hiltViewModel()
) {

    var isShow by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf("") }
    val response = viewModel.noteResponseEvent.value
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if(isLoading)
        LoadingBar()

    LaunchedEffect(key1 = true ){
        viewModel.deleteNoteEvent.collectLatest {
            isLoading = when(it){
                is NoteUiEvents.Success -> {
                    context.showToast("Note Deleted")
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

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            isShow = true
        }, backgroundColor = Red) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "", tint = Color.White)
        }
    }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            AppSearchView(
                search = search, onValueChange = { search = it },
                modifier = Modifier.padding(start = 20.dp, top = 40.dp, end = 20.dp, bottom = 10.dp)
            )

            if (response.data.isNotEmpty())
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize(),
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
                        NoteEachRow(note = it, height = Random.nextInt(150, 300).dp){
                            viewModel.onEvent(NoteEvents.DeleteNoteEvent(it.id))
                        }
                    }
                }


            if (response.isLoading)
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                    CircularProgressIndicator(color = Red)
                }

            if (response.error.isNotEmpty())
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                    Text(text = response.error)
                }

        }


    }

    if (isShow)
        AppAlertDialog {
            isShow = it
        }

}

@Composable
fun AppAlertDialog(
    onShowValue: (Boolean) -> Unit
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {},
        title = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                IconButton(onClick = {
                    onShowValue(false)
                }) {
                    Icon(
                        imageVector = Icons.Default.Close, contentDescription = "", tint = Red
                    )
                }
            }
        },
        text = {
            Column {
                AppTextField(text = title, placeholder = stringResource(R.string.enter_title)) {
                    title = it
                }
                Spacer(modifier = Modifier.height(15.dp))
                AppTextField(
                    text = description,
                    placeholder = "Enter description",
                    modifier = Modifier.height(300.dp)
                ) {
                    description = it
                }
            }
        },
        backgroundColor = Background,
        buttons = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                contentAlignment = Center
            ) {
                Button(
                    onClick = {
                        onShowValue(false)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Red,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(vertical = 15.dp)
                ) { Text(text = stringResource(R.string.save)) }
            }
        },
        shape = RoundedCornerShape(16.dp),
    )

}

@Composable
fun AppTextField(
    text: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {

    TextField(
        value = text, onValueChange = onValueChange, placeholder = {
            Text(
                text = placeholder, style = TextStyle(
                    color = Color.DarkGray, fontSize = 14.sp, fontWeight = FontWeight.Normal
                )
            )
        }, modifier = modifier.fillMaxWidth(), colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )

}

@Composable
fun NoteEachRow(
    note: NoteResponse,
    height: Dp,
    onDelete:()->Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(8.dp))
            .background(ContentColor)
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = note.title, style = TextStyle(
                        color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.W600
                    ),
                    modifier = Modifier
                        .weight(0.7f)
                        .align(CenterVertically)
                )
                IconButton(onClick = {
                                     onDelete()
                }, modifier = Modifier.weight(0.3f)) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "", tint = Red)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = note.description, style = TextStyle(
                    color = Color.Black.copy(alpha = 0.6f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = note.updated_at.split("T")[0], style = TextStyle(
                    color = Color.Black.copy(alpha = 0.3f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }

}

@Composable
fun AppSearchView(
    search: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
    ) {

        TextField(
            value = search, onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = ContentColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.search_notes), style = TextStyle(
                        color = Color.DarkGray, fontSize = 14.sp, fontWeight = FontWeight.Normal
                    )
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search, contentDescription = "",
                    tint = Red
                )
            },
            trailingIcon = {
                if (search.isNotEmpty())
                    IconButton(onClick = {
                        onValueChange("")
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "",
                            tint = Red
                        )
                    }
            }
        )

    }

}

@Composable
fun LoadingBar() {
    Dialog(onDismissRequest = {}) {
        CircularProgressIndicator(color = Red)
    }
}