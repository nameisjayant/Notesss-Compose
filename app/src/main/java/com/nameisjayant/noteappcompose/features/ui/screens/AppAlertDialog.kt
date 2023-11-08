package com.nameisjayant.noteappcompose.features.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nameisjayant.noteappcompose.R
import com.nameisjayant.noteappcompose.ui.theme.Background
import com.nameisjayant.noteappcompose.ui.theme.Red

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppAlertDialog(
    title: String,
    description: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onShowValue: (Boolean) -> Unit,
    onClick: () -> Unit = {}
) {
    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    val controller = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = true) {
        focusRequester.requestFocus()
    }
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
                AppTextField(
                    text = title,
                    placeholder = stringResource(R.string.enter_title),
                    singleLine = true,
                    modifier = Modifier.focusRequester(focusRequester),
                    imeAction = ImeAction.Next,
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ) {
                    onTitleChange(it)
                }

                Spacer(modifier = Modifier.height(15.dp))

                AppTextField(
                    text = description,
                    placeholder = "Enter description",
                    modifier = Modifier.height(300.dp),
                    imeAction = ImeAction.Done,
                    keyboardActions = KeyboardActions(onDone = {
                        controller?.hide()
                    }),
                    onDone = {
                        controller?.hide()
                    }
                ) {
                    onDescriptionChange(it)
                }
            }
        },
        backgroundColor = Background,
        buttons = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        onClick()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Red, contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(vertical = 15.dp),
                    elevation = ButtonDefaults.elevation(0.dp)
                ) { Text(text = stringResource(R.string.save)) }
            }
        },
        shape = RoundedCornerShape(16.dp),
    )

}

@Preview
@Composable
fun AppAlertDialogPreview() {
    AppAlertDialog(
        title = "MyNote",
        description = "Add your notes here",
        onTitleChange = {},
        onDescriptionChange = {},
        onShowValue = {}
    )
}