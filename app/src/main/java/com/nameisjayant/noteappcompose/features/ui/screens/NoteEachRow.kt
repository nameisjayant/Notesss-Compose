package com.nameisjayant.noteappcompose.features.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nameisjayant.noteappcompose.data.model.NoteResponse
import com.nameisjayant.noteappcompose.ui.theme.ContentColor
import com.nameisjayant.noteappcompose.ui.theme.Red

@Composable
fun NoteEachRow(
    note: NoteResponse,
    onUpdate: () -> Unit,
    onDelete: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(ContentColor)
            .clickable { onUpdate() }
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
                    ), modifier = Modifier
                        .weight(0.7f)
                        .align(Alignment.CenterVertically)
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

@Preview
@Composable
fun NoteEachRowPreview() {
    NoteEachRow(
        note = NoteResponse(
            id = 1,
            title = "Notes",
            description = "Sample description",
            created_at = "Yesterday",
            updated_at = "Today"
        ), onUpdate = {}
    ) {

    }
}