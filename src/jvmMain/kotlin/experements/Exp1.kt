package experements

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Тестирование анимании
 */
@Composable
fun testAnimation() {
    val density = LocalDensity.current

    var visible by remember {
        mutableStateOf(true)
    }
    var text: String by remember { mutableStateOf("") }
    AnimatedVisibility(visible = visible, enter = slideInVertically {
        // Slide in from 40 dp from the top.
        with(density) { 40.dp.roundToPx() }
    } + expandVertically(
        // Expand from the top.
        expandFrom = Alignment.Top
    ) + fadeIn(
        // Fade in with the initial alpha of 0.3f.
        initialAlpha = 0.3f
    ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()) {
        Button(onClick = {
            text = "Hello, Desktop!"
            CoroutineScope(Dispatchers.IO).launch {
                visible = false
                delay(1000)
                visible = true
            }
        }) {
            Text(text)
        }
    }
}

/**
 * Тестирование custom view
 */
@Composable
fun customView() {
    val centerColor = MaterialTheme.colorScheme.primaryContainer
    Canvas(modifier = Modifier.fillMaxSize()) {
        val side = minOf(this.size.height, this.size.width)
        drawArc(Color.Black, 0f, 120f, true, size = Size(side, side))
        drawArc(Color.Red, 120f, 120f, true, size = Size(side, side))
        drawArc(Color.Blue, 240f, 120f, true, size = Size(side, side))

        drawCircle(Color.White, side / 15.0f, center = Offset(side / 2, side / 2))
        drawCircle(centerColor, side / 20.0f, center = Offset(side / 2, side / 2))

    }
}