package com.example.projectlogin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
fun ChatScreen(navController: NavController, vm: CBViewModel) {

    val chats = vm.chats.collectAsState().value
    val user = vm.userData.collectAsState().value
    val showDialog = remember { mutableStateOf(false) }

    val onFabClick: () -> Unit = { showDialog.value = true }
    val onDismiss: () -> Unit = { showDialog.value = false }
    val onAddChat: (String) -> Unit = {
        if (it.isNotBlank()) { // Ensure non-empty chat ID
            vm.onAddchat(it)
            showDialog.value = false
        }
    }

    Scaffold(
        floatingActionButton = {
            FAB(
                showDialog = showDialog.value,
                onFabClick = onFabClick,
                onDismiss = onDismiss,
                onAddChat = onAddChat
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                TitleText(text = "Chats")

                if (chats.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "No chats are available ")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(chats) { chat ->
                            val chatUser = if (user != null) {
                                if (chat.user1.userID == user.userId) {
                                    chat.user2
                                } else {
                                    chat.user1
                                }
                            } else {
                                // Handle the case where user is null
                                null
                            }

                            CommonRow(
                                imageUrl = chatUser?.imageUrl,
                                name = chatUser?.name,
                                onItemClick = {
                                    chat.chatId?.let {
                                        navigateto(navController, DestinationScreen.SingleChat.createRoute(id = it))
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun FAB(
    showDialog: Boolean,
    onFabClick: () -> Unit,
    onDismiss: () -> Unit,
    onAddChat: (String) -> Unit
) {
    val addChatMember = remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
                addChatMember.value = ""
            },
            confirmButton = {
                Button(onClick = { onAddChat(addChatMember.value) }) {
                    Text(text = "Add Chat")
                }
            },
            title = { Text(text = "ADD CHAT") },
            text = {
                OutlinedTextField(
                    value = addChatMember.value,
                    onValueChange = { addChatMember.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }
        )
    }

    FloatingActionButton(
        onClick = { onFabClick() },
        containerColor = MaterialTheme.colorScheme.secondary,
        shape = CircleShape,
        modifier = Modifier
            .padding(bottom = 40.dp)
    ) {
        Icon(imageVector = Icons.Rounded.Add, contentDescription = null, tint = Color.Blue)
    }
}

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 35.sp,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun CommonRow(imageUrl: String?, name: String?, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .clickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (imageUrl != null) {
            CommonImage(
                data = imageUrl,
                modifier = Modifier
                    .padding(8.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
            )
        }
        Text(
            text = name ?: "----",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

fun navigateto(navController: NavController, route: String) {
    navController.navigate(route) {
        popUpTo(route)
        launchSingleTop = true
    }
}
