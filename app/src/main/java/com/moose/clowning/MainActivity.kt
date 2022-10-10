package com.moose.clowning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.dsc.form_builder.ChoiceState
import com.dsc.form_builder.FormState
import com.dsc.form_builder.Validators
import com.moose.clowning.ui.theme.ClowningTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ClowningTheme {
                CustomTabs(screen = 0)
            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun CustomTabsPreview() {
        ClowningTheme {
            CustomTabs(screen = 2)
        }
    }


    @Composable
    fun CustomTabs(screen: Int) {
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

        Column(modifier = Modifier.fillMaxSize()) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Page 1",
                        fontWeight = if (screen == 0) FontWeight.Bold else FontWeight.Normal
                    )
                    IndicatorBox(screen = screen, onPlaced = { start = it })
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Page 2",
                        fontWeight = if (screen == 1) FontWeight.Bold else FontWeight.Normal
                    )
                    Canvas(modifier = Modifier.padding(vertical = 12.dp)) {
                        drawCircle(
                            color = black, radius = if (screen == 1) 12f else 8f
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Page 3",
                        fontWeight = if (screen == 2) FontWeight.Bold else FontWeight.Normal
                    )
                    IndicatorBox(screen = screen, onPlaced = { end = it })
                }
            }
        }
    }

    @Composable
    fun IndicatorBox(screen: Int, onPlaced: (Pair<Float, Float>) -> Unit) {
        val black = MaterialTheme.colors.onBackground
        Box(
            modifier = Modifier
                .size(12.dp)
                .onGloballyPositioned { onPlaced(it.getCenter()) }
        ) {
            Canvas(modifier = Modifier.fillMaxSize().padding(vertical = 12.dp)) {
                drawCircle(
                    color = black, radius = if (screen == 2) 12f else 8f
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
