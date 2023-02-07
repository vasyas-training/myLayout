package superCircleView

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
 */
@Composable
fun SuperCircleView(
    modifier: Modifier = Modifier.size(250.dp),
    content: List<CircleElement>,
    offsetAngle: Float = 2f,
    chosenColor: Color = Color.Yellow
) {
    if (content.size > 10){
        throw Exception("Максимальный размер 10")
    }
    if (content.isEmpty())
        return
    var angle_now: Float = 0f
    var angle_mid: Float = 180f / content.size

    Box(modifier = modifier) {
        content.forEach {
            Canvas(modifier.clickable { it.onClick()
            println(angle_now)
            }){
                drawArc(it.background, angle_now, angle_now + angle_mid, false)
            }
            angle_now += angle_mid
        }
    }

}