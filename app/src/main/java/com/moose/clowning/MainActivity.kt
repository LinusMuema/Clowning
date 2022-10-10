package com.moose.clowning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moose.clowning.ui.theme.ClowningTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var screen by mutableStateOf(0)
        val pages: List<String> = (1..5).map { "Page $it" }

        setContent {
            ClowningTheme {
                CustomTabs(screen = screen, pages = pages, onClick = { screen = it })
            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun CustomTabsPreview() {
        val pages: List<String> = (1..5).map { "Page $it" }
        ClowningTheme {
            CustomTabs(screen = 2, pages = pages, onClick = {})
        }
    }


    @Composable
    fun CustomTabs(screen: Int, pages: List<String>, onClick: (Int) -> Unit) {
        var end by remember { mutableStateOf(Pair(0.0f, 0.0f)) }
        var start by remember { mutableStateOf(Pair(0.0f, 0.0f)) }
        val black = MaterialTheme.colors.onBackground

        Canvas(modifier = Modifier.fillMaxWidth()) {
            drawLine(
                color = black,
                strokeWidth = 4f,
                end = Offset(x = end.first, y = end.second),
                start = Offset(x = start.first, y = start.second)
            )
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            pages.forEachIndexed { index, page ->
                Column(
                    modifier = Modifier.clickable { onClick(index) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = page,
                        fontWeight = if (screen == index) FontWeight.Bold else FontWeight.Normal
                    )
                    IndicatorBox(
                        active = screen == index,
                        onPlaced = {
                            when (index) {
                                0 -> start = it
                                pages.lastIndex -> end = it
                            }
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun IndicatorBox(active: Boolean, onPlaced: (Pair<Float, Float>) -> Unit) {
        val black = MaterialTheme.colors.onBackground
        Box(
            modifier = Modifier
                .size(12.dp)
                .onGloballyPositioned { onPlaced(it.getCenter()) }
        ) {
            Canvas(modifier = Modifier.fillMaxSize().padding(vertical = 12.dp)) {
                drawCircle(
                    color = black, radius = if (active) 12f else 8f
                )
            }
        }
    }

    private fun LayoutCoordinates.getCenter(): Pair<Float, Float> {
        val bounds = this.boundsInRoot()
        val diameter = (bounds.right - bounds.left)

        val vertical = bounds.top + diameter
        val horizontal = bounds.left + diameter / 2

        return Pair(horizontal, vertical)
    }
}
