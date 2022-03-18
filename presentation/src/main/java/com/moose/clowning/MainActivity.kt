package com.moose.clowning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moose.clowning.ui.theme.ClowningTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewmodel: MainViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ClowningTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PostsList()
                }
            }
        }
    }

    @Composable
    fun PostsList(){
        val posts by remember { viewmodel.posts }

        LazyColumn{
            items(posts){
                Card(modifier = Modifier.padding(8.dp), elevation = 10.dp) {
                    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Text(text = it.heading, color = Color.Red)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = it.body, color = Color.Black)
                    }
                }
            }
        }
    }
}
