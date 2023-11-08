import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.nameisjayant.noteappcompose.ui.theme.Red

@Composable
fun LoadingBar() {
    Dialog(onDismissRequest = {}) {
        CircularProgressIndicator(color = Red)
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingBarPreview() {
    LoadingBar()
}