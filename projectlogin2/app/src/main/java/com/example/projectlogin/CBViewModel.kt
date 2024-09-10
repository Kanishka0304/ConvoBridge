package com.example.projectlogin

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.example.projectlogin.data.CHATS
import com.example.projectlogin.data.ChatData
import com.example.projectlogin.data.ChatUser
import com.example.projectlogin.data.MESSAGE
import com.example.projectlogin.data.Message
import com.example.projectlogin.data.user
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import java.util.Calendar
import javax.inject.Inject
import kotlin.math.sign

@HiltViewModel
class CBViewModel @Inject constructor(
    val auth: FirebaseAuth,
    var db: FirebaseFirestore
) : ViewModel() {

    val userData = mutableStateOf<user?>(null)
    val chats = mutableStateOf<List<ChatData>>(listOf())
    val chatMessages = mutableStateOf<List<Message>>(listOf())
    val inProgressChatMessage = mutableStateOf(false)
    var currentChatMessageListener: ListenerRegistration? = null
    var signIn  = mutableStateOf(false)

    init {
        val currentUser = auth.currentUser
        signIn.value = currentUser != null
        currentUser?.uid.let {
            if (it != null) {
                getUserData(it)
            }
        }
    }


    fun onSendReply(chatId: String, message: String) {
        val time = Calendar.getInstance().time.toString()
        val msg = Message(userData.value?.userId, message, time)
        db.collection(CHATS).document(chatId).collection(MESSAGE).document().set(msg)
    }

    fun populateMessages(chatId: String) {
        inProgressChatMessage.value = true
        currentChatMessageListener = db.collection(CHATS).document(chatId).collection(MESSAGE)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    handleException(error)
                }
                if (value != null) {
                    chatMessages.value = value.documents.mapNotNull {
                        it.toObject<Message>()
                    }.sortedBy { it.timestamp }
                    inProgressChatMessage.value = false
                }

            }
    }

    fun depopulateMessage() {
        chatMessages.value = listOf()
        currentChatMessageListener = null
    }


    fun getUserData(userId: String) {
        db.collection("users").document(userId).addSnapshotListener { value, error ->
            if (error != null) {
                handleException(error, "Cannot retrieve userdata")
            }
            if (value != null) {
                var user = value.toObject<user>()
                userData.value = user
                populateChats()
            }
        }

    }


    fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e("ConvoBridge", "live chat exception: ", exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if (customMessage.isNullOrEmpty()) errorMsg else customMessage
    }

    fun onAddchat(name: String) {
        if (name.isEmpty()) {
            handleException(customMessage = "Please fill the field ")
        } else {
            db.collection(CHATS).where(
                Filter.or(
                    Filter.and(
                        Filter.equalTo("user1.name", name),
                        Filter.equalTo("user2.name", userData.value?.name)
                    ),
                    Filter.and(
                        Filter.equalTo("user1.name", userData.value?.name),
                        Filter.equalTo("user2.name", name)
                    )
                )
            ).get().addOnSuccessListener { it ->
                if (it.isEmpty) {
                    db.collection("users").whereEqualTo("name", name).get()
                        .addOnSuccessListener {
                            if (it.isEmpty) {
                                handleException(customMessage = "Name not found")
                            } else {
                                val chatPartner = it.toObjects<user>()[0]
                                val id = db.collection(CHATS).document().id
                                val chat = ChatData(
                                    chatId = id,
                                    ChatUser(
                                        userData.value?.userId,
                                        userData.value?.name,
                                        userData.value?.email
                                    ), ChatUser(
                                        chatPartner.userId,
                                        chatPartner.name,
                                        chatPartner.email
                                    )
                                )
                                db.collection(CHATS).document(id).set(chat)
                            }
                        }
                        .addOnFailureListener{
                            handleException(it)
                        }
                } else {
                    handleException(customMessage = "Chat already exist")
                }
            }
        }
    }



    fun populateChats(){
        db.collection(CHATS).where(
            Filter.or(
                Filter.equalTo("user1.userId", userData.value?.userId),
                Filter.equalTo("user2.userId", userData.value?.userId)
            )
        ).addSnapshotListener{
            value, error ->
            if(error!=null){
                handleException(error)
            }
            if (value!=null){
                chats.value = value.documents.mapNotNull {
                    it.toObject<ChatData>()
                }
            }
        }


    }


}
