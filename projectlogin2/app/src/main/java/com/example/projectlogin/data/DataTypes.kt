package com.example.projectlogin.data

data class User(
    var userId: String? = "",
    var email: String? = "",
    var name: String? = ""
) {
    fun toMap() = hashMapOf(
        "userId" to userId,
        "email" to email,
        "name" to name
    )
}

data class ChatData(
    val chatId: String? = "",
    val user1: ChatUser = ChatUser(),
    val user2: ChatUser = ChatUser()
)

data class ChatUser(
    val userID: String? = "",
    val name: String? = "",
    val number: String? = "",
    val imageUrl: String? = ""
)

data class Message(
    val sentby: String? = "",
    val message: String? = "",
    val timestamp: String? = ""
)

data class Profile(
    val name: String = "",
    val username: String = "",
    val bio: String = "",
    val imageUri: String = ""
)
