package utils

import androidx.compose.ui.unit.Dp
import kotlin.math.atan2

/**
 * Функция, которая преобразует точку в угол
 * @author Bogdan Khrystov
 * @author Панков Вася
 */
fun convertTouchEventPointToAngle(xPos: Float, yPos: Float, width: Dp, height: Dp): Double {
    var x = xPos - (width.value * 0.5f)
    val y = yPos - (height.value * 0.5f)

    var angle = Math.toDegrees(atan2(y.toDouble(), x.toDouble()) + Math.PI / 2)
    angle = if (angle < 0) angle + 360 else angle
    return angle
}