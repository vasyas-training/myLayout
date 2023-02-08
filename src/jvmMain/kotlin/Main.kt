import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import layouts.circleLayout
import layouts.goldenRatioLayout
import superCircleView.CircleElement
import superCircleView.superCircleView


/**
 * Класс тестирует Layout круга
 */
@Composable
fun testCircleLayout() {
    var counter by remember { mutableStateOf(4) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Необходимо оборачивать в Box, из-за того что

        circleLayout {
            repeat(counter) {
                // Text("Test $it", softWrap = true)
                Button(onClick = {}) {
                    Text("Test $it")
                }
            }
        }


        var visible by remember { mutableStateOf(true) }
        if (visible) Button(
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
fun testGoldenLayout() {
    var counter by remember { mutableStateOf(1) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Необходимо оборачивать в Box, из-за того что

        goldenRatioLayout {
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
        if (visible) Button(
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
fun testSuperCircleLayout() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        superCircleView(content = listOf(CircleElement("Test") {
            Image(painterResource("test.jpg"), "Моя фотка")
        }, CircleElement("Test", background = Color.Blue) {
            Image(painterResource("test.jpg"), "Моя фотка")
        }, CircleElement("Test", background = Color.Yellow) {
            Image(painterResource("test.jpg"), "Моя фотка")
        }, CircleElement("Test", background = Color.LightGray) {
            Image(painterResource("test.jpg"), "Моя фотка")
        })
        )
    }

}


/**
 * Основная функция приложения
 *
 */
@Composable
@Preview
fun App() {
    MaterialTheme {
        LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Adaptive(minSize = 500.dp)) {
            item {
                testCircleLayout()
            }
            item {
                testGoldenLayout()
            }
            item { testSuperCircleLayout() }

        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
