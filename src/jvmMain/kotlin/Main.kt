import androidx.compose.animation.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import layouts.CircleLayout
import layouts.GoldenRatioLayout
import superCircleView.CircleElement
import superCircleView.SuperCircleView


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

/**
 * Класс тестирует Layout круга
 */
@Composable
fun TestCircleLayout() {
    var counter by remember { mutableStateOf(1) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Необходимо оборачивать в Box, из-за того что

        CircleLayout {
            repeat(counter) {
                // Text("Test $it", softWrap = true)
                Button(onClick = {}) {
                    Text("Test $it")
                }
            }
        }


        var visible by remember { mutableStateOf(true) }
        if (visible)
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        visible = false
                        repeat(1) {
                            delay(10)
                            counter += 1

                        }
                        visible = true
                    }
                },
            ) {
                Text("Старт")

            }
        Text("Количество элементов: $counter")
    }
}

/**
 * Класс тестирует Layout золотого сечения
 */
@Composable
fun TestGoldenLayout() {
    var counter by remember { mutableStateOf(1) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Необходимо оборачивать в Box, из-за того что

        GoldenRatioLayout {
            repeat(counter) {
                // Text("Test $it", softWrap = true)
                Box(
                    Modifier.fillMaxSize().padding(5.dp)
                        .background(Brush.horizontalGradient(listOf(Color.White, Color.Blue)))
                ) {
                    Text("Здесь был Вася")
                }
                // Image(painterResource("test.jpg"), null)
            }
        }


        var visible by remember { mutableStateOf(true) }
        if (visible)
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        visible = false
                        repeat(1) {
                            delay(10)
                            counter += 1

                        }
                        visible = true
                    }
                },
            ) {
                Text("Старт")

            }
        Text("Количество элементов: $counter")
    }
}

@Composable
fun TestSuperCirleLayout() {
    SuperCircleView(content = listOf(CircleElement(painterResource("test.jpg"), "Test")))
}


/**
 * Основная функция приложения
 *
 */
@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }


    MaterialTheme {
        LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Adaptive(minSize = 500.dp)) {
            item {
                TestCircleLayout()
            }
            item {
                TestGoldenLayout()
            }
            item { TestSuperCirleLayout() }

        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

fun test(): String {
    return ""
}