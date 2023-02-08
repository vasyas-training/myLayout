package superCircleView

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import layouts.emptyCircleLayout
import utils.convertTouchEventPointToAngle

/**
 * View, который представляет из себя значок выбора, как из GTA 5 RP
 *
 * TODO
 *
 * @param modifier
 * размер View в пространстве
 * @param content
 * список элементов, которые будут отображаться
 * @param offsetAngle
 * угол, который будет разделять элементы TODO
 * @param chosenColor
 * цвет, который будет отображаться при выборе элемента
 * @param centerColor
 * цвет, который отображается в центре
 *
 * @author Панков Вася
 * @author nglauber
 */
@Composable
fun superCircleView(
    modifier: Modifier = Modifier.size(250.dp),
    content: List<CircleElement>,
    offsetAngle: Float = 2f,
    chosenColor: Color = Color.Yellow,
    centerColor: Color = Color.Gray
) {
    if (content.size > 10) {
        throw Exception("Максимальный размер 10")
    }
    if (content.isEmpty())
        return
    var angle_now: Float = -90f
    var angle_mid: Float = 360f / content.size
    // println(angle_mid)
    /*var width: Int = 0
    var height: Int = 0*/

    BoxWithConstraints(modifier = modifier) {
        var chosenArc by remember { mutableStateOf(-1) }
        Canvas(modifier.pointerInput(Unit) {
            detectTapGestures { offsetAngle ->
                val angle = convertTouchEventPointToAngle(
                    offsetAngle.x,
                    offsetAngle.y,
                    this@BoxWithConstraints.maxWidth,
                    this@BoxWithConstraints.maxHeight
                )
                /*println(
                    "$offsetAngle, angle: ${
                        convertTouchEventPointToAngle(
                            offsetAngle.x,
                            offsetAngle.y,
                            this@BoxWithConstraints.maxWidth,
                            this@BoxWithConstraints.maxHeight
                        )
                    }"
                )*/
                chosenArc = (angle / angle_mid).toInt()
                content[chosenArc].onClick()

            }
        }) {
            content.forEach {
                drawArc(it.background, angle_now, angle_mid, true)
                angle_now += angle_mid
            }


        }

        Canvas(modifier = modifier) {
            val side = minOf(this.size.height, this.size.width)
            drawCircle(
                Color.White,
                side / 15.0f,
                center = if (side == this.size.height) Offset(this.center.x, side / 2f) else Offset(
                    side / 2f,
                    this.center.y
                )
            )
            if (chosenArc != -1) {
                drawArc(
                    chosenColor,
                    chosenArc * angle_mid - 90,
                    angle_mid,
                    true,
                    size = Size(side / 5.0f, side / 5.0f),
                    topLeft = Offset(this.center.x - side / 10.0f, this.center.y - side / 10.0f)
                )
            }
            drawCircle(
                centerColor,
                side / 20.0f,
                center = if (side == this.size.height) Offset(this.center.x, side / 2f) else Offset(
                    side / 2f,
                    this.center.y
                )
            )

        }
        emptyCircleLayout(modifier = modifier, startAngle = -90f) {
            content.forEach {
                it.content()
            }
        }
    }


}