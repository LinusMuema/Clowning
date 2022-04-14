package com.moose.clowning.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moose.clowning.messages.MessagesActivity
import com.moose.clowning.ui.theme.ClowningTheme
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : ComponentActivity() {
    private val viewmodel: MainViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ClowningTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = {
                                    val id = viewmodel.createChat()

                                    val intent = Intent(this, MessagesActivity::class.java)
                                    intent.putExtra("chatId", id)
                                    startActivity(intent)
                                },
                                content = {
                                    Icon(Icons.Default.Add, contentDescription = "add fab")
                                }
                            )
                        },
                        content = {
                            ChatsList()
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun ChatsList() {
        // toast any error
        val error by remember { viewmodel.error }
        error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }


        val chats by remember { viewmodel.chats }
        LazyColumn {
            items(chats) {
                it?.let {
                    Card(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                        Box(
                            modifier = Modifier.clickable {
                                val intent = Intent(this@MainActivity, MessagesActivity::class.java)
                                intent.putExtra("chatId", it.id)
                                startActivity(intent)
                            },
                            content = {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(text = it.id)
                                    Text(text = it.createdAt)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
