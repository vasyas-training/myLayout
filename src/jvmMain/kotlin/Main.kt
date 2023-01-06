// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.animation.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.*
import kotlin.random.Random

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

@Composable
fun CircleLayout(
    modifier: Modifier = Modifier.fillMaxSize(),
    colors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.surfaceVariant
    ),
    randomColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val centerColor = MaterialTheme.colorScheme.primaryContainer
    var placeableSize by remember { mutableStateOf(0) }

    // Отрисовывем задний фон
    Canvas(modifier = modifier) {
        val side = minOf(this.size.height, this.size.width)
        // println("not this side = ${side}")

        val sweepAngle = 360.0f / placeableSize
        var startAngle = 0f
        repeat(placeableSize) {
            drawArc(
                if (randomColor) Color(
                    Random.nextInt(0, 360),
                    Random.nextInt(0, 360),
                    Random.nextInt(0, 360)
                ) else colors[it % colors.size],
                startAngle,
                sweepAngle,
                true,
                topLeft = if (side == this.size.height) Offset(this.center.x - side / 2f, 0f) else Offset(
                    0f,
                    this.center.y - side / 2f
                ),
                size = Size(side, side)
            )
            startAngle += sweepAngle;
        }
        // Отрисовка центра
        drawCircle(
            Color.White,
            side / 15.0f,
            center = if (side == this.size.height) Offset(this.center.x, side / 2f) else Offset(
                side / 2f,
                this.center.y
            )
        )
        drawCircle(
            centerColor,
            side / 20.0f,
            center = if (side == this.size.height) Offset(this.center.x, side / 2f) else Offset(
                side / 2f,
                this.center.y
            )
        )

    }

    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        placeableSize = measurables.size
        val side = minOf(constraints.maxWidth, constraints.maxHeight)
        val sweepAngle = 360.0f / placeableSize
        val R = side / 2.0f

        // Длина средней хорды * 0.8
        var minVal = ((R * PI) / (measurables.size * 1.2)).toInt()



        if (minVal == 0) {
            minVal = (R / 2).toInt()
        }


        // Задаю свои параметры для элементов
        val myConstraints = Constraints(0, minVal, 0, minVal)

        // По  хорошему здесь реализовать логика подбору по элемену
        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(myConstraints)

        }


        // println("this side = ${side}")

        layout(side, side) {

            // Track the y co-ord we have placed children up to
            var angle = 0f

            val centerPos = Pair(side / 2.0f, side / 2.0f)


            if (placeableSize == 1){
                placeables[0].placeRelative(
                    x = (centerPos.first + R / 2 * cos(
                        Math.toRadians((angle + sweepAngle / 2).toDouble()).toFloat()
                    )).toInt(),
                    y = (centerPos.second + R / 2 * sin(
                        Math.toRadians((angle + sweepAngle / 2).toDouble()).toFloat()
                    )).toInt()
                )
            } else {
                // Отрисовка layout
                placeables.forEachIndexed { index, placeable ->
                    // Position item on the screen
                    // measurables[index].minIntrinsicWidth(placeable.height) - получает длину объекта, который у нас есть,
                    // сделано для корректного отображение объекта без смещения
                    // Находим x и y по формуле: координата центра плюс радиус умноженный на cos радианы половины угла.
                    // То есть центр.
                    var minusX = measurables[index].minIntrinsicWidth(placeable.height) / 2
                    var minusY = measurables[index].minIntrinsicWidth(placeable.height) / 2
                    if (minusX > minVal){
                        minusX = minVal / 2
                    }
                    if (minusY > minVal){
                        minusY = minVal / 2
                    }
                    placeable.placeRelative(
                        x = (centerPos.first + R / 2 * cos(
                            Math.toRadians((angle + sweepAngle / 2).toDouble()).toFloat()
                        )).toInt() - minusX,
                        y = (centerPos.second + R / 2 * sin(
                            Math.toRadians((angle + sweepAngle / 2).toDouble()).toFloat()
                        )).toInt() - minusY
                    )

                    angle += sweepAngle


                }
            }
        }
    }


}

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }


    MaterialTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            var counter by remember { mutableStateOf(1) }

            Box(Modifier.size(600.dp)) {

                // customView()
                CircleLayout(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                    repeat(counter) {
                        // Text("Test $it", softWrap = true)
                        Button(onClick = {}) {
                            Text("Test $it")
                        }
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
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

fun test(): String {
    return "";
}