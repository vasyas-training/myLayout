package superCircleView

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
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
 * угол, который будет разделять элементы
 * @param chosenColor
 * цвет, который будет отображаться при выборе элемента
 *
 * @author Панков Вася
 * @author nglauber
 */
@Composable
fun superCircleView(
    modifier: Modifier = Modifier.size(250.dp),
    content: List<CircleElement>,
    offsetAngle: Float = 2f,
    chosenColor: Color = Color.Yellow
) {
    if (content.size > 10) {
        throw Exception("Максимальный размер 10")
    }
    if (content.isEmpty())
        return
    var angle_now: Float = -90f
    var angle_mid: Float = 360f / content.size
    println(angle_mid)
    var width: Int = 0
    var height: Int = 0

    BoxWithConstraints(modifier = modifier) {

        Canvas(modifier.pointerInput(Unit) {
            detectTapGestures { offsetAngle ->
                val angle = convertTouchEventPointToAngle(
                    offsetAngle.x,
                    offsetAngle.y,
                    this@BoxWithConstraints.maxWidth,
                    this@BoxWithConstraints.maxHeight
                )
                println(
                    "$offsetAngle, angle: ${
                        convertTouchEventPointToAngle(
                            offsetAngle.x,
                            offsetAngle.y,
                            this@BoxWithConstraints.maxWidth,
                            this@BoxWithConstraints.maxHeight
                        )
                    }"
                )
                println((angle / angle_mid).toInt())
            }
        }) {
            content.forEach {

                drawArc(it.background, angle_now, angle_mid, true)
                angle_now += angle_mid
            }
        }
    }

}