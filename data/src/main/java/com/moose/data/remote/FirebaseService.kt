package com.moose.data.remote

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.moose.data.models.Chat
import com.moose.data.models.Message
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

object FirebaseService {

    private val database = Firebase.database
    private val reference = database.reference

    fun createChat(): String {
        val chatId = reference.child("chats").push().key

        val time = System.currentTimeMillis().toString()
        val chat = Chat(id = chatId!!, createdAt = time)
        reference.child("chats").child(chatId).setValue(chat)

        return chatId
    }

    fun sendMessage(chatId: String, message: Message){
        reference.child("chats").child(chatId).push().setValue(message)
    }

    fun getChats() = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                trySend(Result.failure(Throwable(error.message)))
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("Chats", "onDataChange: children are ${snapshot.children}")
                val chats = snapshot.children.map { it.getValue(Chat::class.java) }
                trySend(Result.success(chats))
            }
        }

        reference.child("chats").addValueEventListener(listener)
        awaitClose {
            reference.child("chats").removeEventListener(listener)
        }
    }

    fun getChatMessages(chatId: String) =  callbackFlow {
        val listener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                trySend(Result.failure(Throwable(error.message)))
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = snapshot.children
                    .filter { it.hasChildren() }
                    .map { it.getValue(Message::class.java) }
                trySend(Result.success(messages))
            }
        }

        reference.child("chats").child(chatId).addValueEventListener(listener)
        awaitClose {
            reference.child("chats").child(chatId).removeEventListener(listener)
        }
    }
}