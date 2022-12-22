package com.ironmeddie.gridcomposable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun VerticalGrid(
    modifier: Modifier = Modifier,
    collumns: Int = 2,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val itemWidth = constraints.maxWidth / collumns
        val itemConstraints = constraints.copy(minWidth = itemWidth, maxWidth = itemWidth)
        val placeables = measurables.map { it.measure(itemConstraints) }
        layout(constraints.maxWidth,  measureHeight(placeables, collumns)) {
            var x = 0
            var y = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(x, y)
                if (x + placeable.width <= constraints.maxWidth - placeable.width) {
                    x += placeable.width
                } else {
                    x = 0
                    y += placeable.measuredHeight
                }
            }
        }
    }
}


private fun measureHeight(placeables: List<Placeable>, collumns: Int): Int{
    var height = 0
    val list = mutableListOf<Int>()
    var i = 0
    placeables.forEach { placeable ->
        list.add(placeable.height)
        i++
        if (i == collumns) {
            height += list.max()
            i = 0
            list.clear()
        }
    }
    if (list.isNotEmpty()) height += list.max()
    return height
}


@Composable
fun test() {
    VerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
     ,2
    ) {
        for (i in 1..9) {
            Box(
                Modifier
                    .height(150.dp)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.DarkGray), contentAlignment = Alignment.Center
            ) {
                Text(text = "element $i")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun prev() {
    test()
}