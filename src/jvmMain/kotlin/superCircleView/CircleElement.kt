package superCircleView

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

/**
 * Элемент CircleView
 * @param painter
 * рисунок, который будет отображаться
 * @param description
 * описание при выделении
 * @param onClick
 * действие при нажатии
 * @param background
 * Цвет заднего фона
 *
 * @author Панков Вася
 */


data class CircleElement(
    val painter: Painter,
    val description: String,
    val onClick: () -> Unit = {},
    val background: Color = Color.Gray)


