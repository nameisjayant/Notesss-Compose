package com.nameisjayant.noteappcompose.features.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nameisjayant.noteappcompose.R
import com.nameisjayant.noteappcompose.ui.theme.ContentColor
import com.nameisjayant.noteappcompose.ui.theme.Red

@Composable
fun AppSearchView(
    search: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = search,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
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
                imageVector = Icons.Outlined.Search, contentDescription = "", tint = Red
            )
        },
        trailingIcon = {
            if (search.isNotEmpty()) IconButton(onClick = {
                onValueChange("")
            }) {
                Icon(
                    imageVector = Icons.Outlined.Close, contentDescription = "", tint = Red
                )
            }
        },
        shape = RoundedCornerShape(10.dp)
    )

}

@Preview
@Composable
fun AppSearchViewPreview() {
    AppSearchView(search = "", onValueChange = {})
}