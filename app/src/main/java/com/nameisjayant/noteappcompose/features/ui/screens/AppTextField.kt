package com.nameisjayant.noteappcompose.features.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp

@Composable
fun AppTextField(
    text: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    onNext: () -> Unit = {},
    onDone: () -> Unit = {},
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
        ),
        singleLine = singleLine,
        keyboardActions = KeyboardActions(
            onDone = { onDone() },
            onNext = { onNext() }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction
        )
    )

}