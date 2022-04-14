package com.moose.clowning.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moose.clowning.ui.theme.ClowningTheme

class MessagesActivity : AppCompatActivity() {
    private lateinit var chatId: String
    private val viewmodel: MessagesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chatId = intent.getStringExtra("chatId")!!
        viewmodel.getMessages(chatId)

        setContent {
            ClowningTheme {
                Surface {
                    Column {
                        Box(modifier = Modifier.weight(1f)) {
                            Messages()
                        }
                        MessageBox()
                    }
                }
            }
        }
    }

    @Composable
    fun Messages() {
        // toast any error
        val error by remember { viewmodel.error }
        error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }

        val userId = viewmodel.randomUser
        val messages by remember { viewmodel.messages }

        LazyColumn {
            items(messages) { message ->
                message?.let {
                    val color = if (message.user == userId) Color.Blue else Color.Red
                    val nameTag = if (message.user == userId) "You" else message.user
                    Card(
                        backgroundColor = color,
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        content = {
                            Column {
                                Text(text = message.text)
                                Text(text = "From: $nameTag")
                            }
                        }
                    )

                }
            }
        }
    }


    @Composable
    fun MessageBox() {
        var message by remember { mutableStateOf("") }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.weight(1f)) {
                TextField(
                    value = message,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { message = it }
                )
            }
            IconButton(
                onClick = {
                    if (message.isNotEmpty()) {
                        viewmodel.sendMessage(chatId = chatId, message)
                    } else {
                        Toast.makeText(
                            this@MessagesActivity,
                            "Message is empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                content = {
                    Icon(Icons.Default.Send, contentDescription = "send button")
                }
            )
        }
    }
}