package com.nameisjayant.noteappcompose.features.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nameisjayant.noteappcompose.R
import com.nameisjayant.noteappcompose.data.model.Note
import com.nameisjayant.noteappcompose.ui.theme.Background
import com.nameisjayant.noteappcompose.ui.theme.ContentColor
import com.nameisjayant.noteappcompose.ui.theme.Red


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteScreen() {

    var isShow by remember { mutableStateOf(false) }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            isShow = true
        }, backgroundColor = Red) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "", tint = Color.White)
        }
    }) {

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
                IconButton(onClick = {}) {
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
                AppTextField(text = description, placeholder = "Enter description") {
                    description = it
                }
            }
        },
        backgroundColor = Background,
        buttons = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = {
                        onShowValue(false)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Red
                    )
                ) { Text(text = stringResource(R.string.save)) }
            }
        },
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
    note: Note
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(ContentColor)
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text = note.title, style = TextStyle(
                    color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.W600
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = note.description, style = TextStyle(
                    color = Color.Black.copy(alpha = 0.6f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = note.date, style = TextStyle(
                    color = Color.Black.copy(alpha = 0.3f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }

}