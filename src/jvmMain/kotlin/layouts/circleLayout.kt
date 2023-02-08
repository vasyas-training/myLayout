package layouts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random


/**
 * Layout, который распределяет элемент по кругу
 * @param modifier
 * настройки размеров в пространстве
 * @param colors
 * цвета, которые будут у круга
 * @param randomColor
 * при ``true``
 * @param content
 * содержимое Layout
 *
 * @author Панков Вася
 */
@Composable
fun circleLayout(
    modifier: Modifier = Modifier.size(250.dp),
    colors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.surfaceVariant
    ),
    randomColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val centerColor = MaterialTheme.colorScheme.primaryContainer
    var placeableSize = 0

    // Получаем количество элементов заранее
    Layout(
        modifier = Modifier.size(0.dp),
        content = content
    ) { measurables, constraints ->
        placeableSize = measurables.size
        layout(0, 0){

        }
    }


    //  Необходимо завернуть в Box, чтобы элементы адекватно отрисовывались, то есть друг на другу,
    //  а например не попадали под свойства оборнутых сверху, Column,
    Box(modifier = modifier) {
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
                startAngle += sweepAngle
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

        emptyCircleLayout(modifier = modifier) { content() }
    }


}