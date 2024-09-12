package com.example.projectlogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectlogin.data.ChatData
import com.example.projectlogin.data.Message
import com.example.projectlogin.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CBViewModel(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {

    private val _userData = MutableStateFlow<User?>(null)
    val userData: StateFlow<User?> get() = _userData

    private val _chatMessages = MutableStateFlow<List<Message>>(emptyList())
    val chatMessages: StateFlow<List<Message>> get() = _chatMessages

    private val _chats = MutableStateFlow<List<ChatData>>(emptyList())
    val chats: StateFlow<List<ChatData>> get() = _chats

    init {
        fetchUserData()
        fetchChats() // Initialize chat data
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            try {
                val userSnapshot = db.collection("User").document(userId).get().await()
                val user = userSnapshot.toObject(User::class.java)
                _userData.value = user
            } catch (e: Exception) {
                // Handle error
                _userData.value = null
            }
        }
    }

    private fun fetchChats() {
        viewModelScope.launch {
            try {
                val userId = auth.currentUser?.uid ?: return@launch
                val chatsSnapshot = db.collection("Chats").whereArrayContains("userIDs", userId).get().await()
                val chats = chatsSnapshot.toObjects(ChatData::class.java)
                _chats.value = chats
            } catch (e: Exception) {
                // Handle error
                _chats.value = emptyList()
            }
        }
    }

    fun populateMessages(chatId: String) {
        viewModelScope.launch {
            try {
                val messagesSnapshot = db.collection("Chats").document(chatId).collection("messages").get().await()
                val messages = messagesSnapshot.toObjects(Message::class.java)
                _chatMessages.value = messages
            } catch (e: Exception) {
                // Handle error
                _chatMessages.value = emptyList()
            }
        }
    }

    fun depopulateMessage() {
        _chatMessages.value = emptyList()
    }

    fun onSendReply(chatId: String, reply: String) {
        viewModelScope.launch {
            try {
                val message = Message(
                    message = reply,
                    sentby = auth.currentUser?.uid,
                    timestamp = System.currentTimeMillis().toString()
                )
                db.collection("Chats").document(chatId).collection("messages").add(message).await()
                populateMessages(chatId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun onAddchat(chatMemberId: String) {
        viewModelScope.launch {
            try {
                val currentUserId = auth.currentUser?.uid ?: return@launch

                // Create a new chat document
                val chatId = generateUniqueChatId()
                val chat = ChatData(
                    chatId = chatId,
                    user1 = User(userId = currentUserId),
                    user2 = User(userId = chatMemberId)
                )

                // Add the chat to Firestore
                db.collection("Chats").document(chatId).set(chat).await()

                // Fetch updated list of chats
                fetchChats()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun generateUniqueChatId(): String {
        // Generate a unique ID for the chat
        return System.currentTimeMillis().toString()
    }
}
