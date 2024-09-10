package com.example.projectlogin.data

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.Timestamp

data class user (
    var userId: String?="",
    var email : String?="",
    var name :String?=""
) {
    fun toMap() = hashMapOf(
    "userId" to userId,
        "email" to email,
    "username" to name)
}


//
//data class UserData(
//    var userId: String?="",
//    var name: String?="",
//    var number: String?="",
//    var imageUrl: String?="",
//    var email: String?="",
//
//    ){
//    fun toMap() = mapOf(
//        "userId" to userId,
//        "name" to name,
//        "number" to number,
//        "imageUrl" to imageUrl,
//        "email" to email
//    )
//}

data class ChatData(
    val chatId: String?="",
    val user1: ChatUser = ChatUser(),
    val user2: ChatUser = ChatUser()

)

data class ChatUser (
    val userID: String?="",
    val name: String?="",
    val number: String?="",
    val imageUrl: String?="",
)

data class Message(
    var sentby : String?="",
    val message : String?="",
    val timestamp: String?=""
) {
}

data class Profile(
    val name: String = "",
    val username: String = "",
    val bio: String = "",
    val imageUri: String = ""
)

