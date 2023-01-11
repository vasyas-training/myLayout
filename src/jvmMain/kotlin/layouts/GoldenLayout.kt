package layouts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp

/** Layout, который представляет из себя золотое сечение
 * Сделан, так как видел его нужду в некоторых проектах на GitHub
 * Если получится опубликую, как библиотеку.
 *
 * Всё строил на основе информации из википедии:
 *
 * https://ru.wikipedia.org/wiki/%D0%97%D0%BE%D0%BB%D0%BE%D1%82%D0%BE%D0%B5_%D1%81%D0%B5%D1%87%D0%B5%D0%BD%D0%B8%D0%B5
 *
 * https://ru.wikipedia.org/wiki/%D0%A7%D0%B8%D1%81%D0%BB%D0%B0_%D0%A4%D0%B8%D0%B1%D0%BE%D0%BD%D0%B0%D1%87%D1%87%D0%B8
 */
@Composable
fun GoldenRatioLayout(
    modifier: Modifier = Modifier.size(340.dp, 210.dp),
    content: @Composable () -> Unit
) {
    // Примерные значения золотого сечения
    val ratio = Pair(0.62f, 0.38f)
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        var newConstraints = Constraints(0, (minOf(constraints.maxWidth, constraints.maxHeight) * ratio.first).toInt(), 0, (minOf(constraints.maxHeight, constraints.maxWidth) * ratio.first).toInt())
        newConstraints = newConstraints.copy(maxHeight = newConstraints.maxWidth)


        val constraintsList = mutableListOf<Constraints>()
        val placeables = measurables.mapIndexed { index, measurable ->

            if (index != 0)
                newConstraints = if (index == 1 && measurables.size == 2){
                    newConstraints.copy(maxWidth = (newConstraints.maxHeight * ratio.first).toInt())
                } else {
                    newConstraints.copy(
                        maxHeight = (newConstraints.maxHeight * ratio.first).toInt(),
                        maxWidth = (newConstraints.maxHeight * ratio.first).toInt()
                    )
                }

            constraintsList.add(newConstraints.copy())
            measurable.measure(newConstraints)

        }
        layout(constraints.maxWidth, constraints.maxHeight) {
            var isReverse = true
            var y = 0
            var x = 0
            placeables.forEachIndexed{ index, placeble ->
                if (index % 2 == 0){
                    isReverse = !isReverse
                    if (isReverse){
                        x -= constraintsList[index].maxWidth
                    }
                    placeble.place(x, y)
                    if (!isReverse) {
                        x += constraintsList[index].maxWidth
                    }
                } else{
                    val y_temp = y
                    if (isReverse){
                        x -= constraintsList[index].maxWidth
                        if (index + 1 != measurables.size) {
                            y += constraintsList[index + 1].maxHeight
                        }
                    }
                    placeble.place(x, y)
                    if (!isReverse) {
                        x += constraintsList[index].maxWidth
                        y += constraintsList[index].maxHeight
                    }
                    else if (index + 1 != measurables.size){
                        y -= constraintsList[index + 1].maxHeight
                    }
                }
                // println("$x $y $isReverse")

            }
        }
    }

}