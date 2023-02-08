package layouts

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Layout распределяющий элементы по кругу, без заднего фона
 * @param modifier
 * настройки размеров в пространстве
 * @param startAngle
 * Начальный угол
 * @param content
 * содержимое Layout
 * @author Панков Вася
 */

@Composable
fun emptyCircleLayout(
    modifier: Modifier = Modifier.size(250.dp),
    startAngle: Float = 0f,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeableSize = measurables.size
        val side = minOf(constraints.maxWidth, constraints.maxHeight)
        val sweepAngle = 360.0f / placeableSize
        val r = side / 2.0f

        // Длина средней хорды * 0.8
        var minVal = ((r * PI) / (placeableSize)).toInt()


//            if (minVal == 0) {
//                println((r * PI) / (measurables.size * 1.2))
//                minVal = (r / 2).toInt()
//            }


        // Задаю свои параметры для элементов
        val myConstraints = Constraints(0, minVal, 0, minVal)

        // По  хорошему здесь реализовать логику подбору по элемену
        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(myConstraints)

        }


        // println("this side = ${side}")

        layout(side, side) {

            // Track the y co-ord we have placed children up to
            var angle = startAngle

            val centerPos = Pair(side / 2.0f, side / 2.0f)


            /*  Существует проблема располажения в середине части окружности от количества элементов
                Возможно из-за погрешности, но исправление этого пока весьма странное и выглядит как костыль
             */

            if (placeableSize == 1) {
                placeables[0].placeRelative(
                    x = (centerPos.first + r / 2 * cos(
                        Math.toRadians((angle + sweepAngle / 2).toDouble()).toFloat()
                    )).toInt(),
                    y = (centerPos.second + r / 2 * sin(
                        Math.toRadians((angle + sweepAngle / 2).toDouble()).toFloat()
                    )).toInt() + minVal / 4
                )
            } else if (placeableSize < 6) {
                val minusX = (minVal / 2.2).toInt()

                val minusY = (minVal / 4).toInt()
                placeables.forEach { placeable ->
                    placeable.placeRelative(
                        x = (centerPos.first + r / 2 * cos(
                            Math.toRadians((angle + sweepAngle / 2).toDouble()).toFloat()
                        )).toInt() - minusX,
                        y = (centerPos.second + r / 2 * sin(
                            Math.toRadians((angle + sweepAngle / 2).toDouble()).toFloat()
                        )).toInt() - minusY
                    )
                    angle += sweepAngle
                }
            } else {
                // Отрисовка layout
                placeables.forEach { placeable ->
                    // Position item on the screen
                    // measurables[index].minIntrinsicWidth(placeable.height) - получает длину объекта, который у нас есть,
                    // сделано для корректного отображение объекта без смещения
                    // Находим x и y по формуле: координата центра плюс радиус умноженный на cos радианы половины угла.
                    // То есть центр.
                    /*var minusX = measurables[index].minIntrinsicWidth(placeable.height) / 2
                    var minusY = measurables[index].minIntrinsicHeight(placeable.width) / 2
                    println(minusX)
                    println(minusY)
                    println(minVal)*/
                    val minusX = minVal / 2

                    val minusY = minVal / 2

                    placeable.placeRelative(
                        x = (centerPos.first + r / 2 * cos(
                            Math.toRadians((angle + sweepAngle / 2).toDouble()).toFloat()
                        )).toInt() - minusX,
                        y = (centerPos.second + r / 2 * sin(
                            Math.toRadians((angle + sweepAngle / 2).toDouble()).toFloat()
                        )).toInt() - minusY
                    )

                    angle += sweepAngle


                }
            }
        }
    }
}