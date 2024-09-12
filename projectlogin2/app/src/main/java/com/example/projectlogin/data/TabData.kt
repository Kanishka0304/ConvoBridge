package com.example.projectlogin.data

data class TabData(
    val title: String,
    val unreadCount: Int? = null  // Default to null if not provided
)

val tabs = listOf(
    TabData(title = Tabs.CHATS.value),  // "Chats" tab
    TabData(title = Tabs.CALLS.value)   // "Calls" tab
)

enum class Tabs(val value: String) {
    CHATS("Chats"),
    CALLS("Calls")
}
