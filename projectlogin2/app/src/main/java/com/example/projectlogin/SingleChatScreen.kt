@file:Suppress("KotlinConstantConditions")

package com.example.projectlogin

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.projectlogin.data.Message
import com.example.projectlogin.ui.theme.Purple80

@Composable
fun UserChatScreen(navController: NavController, vm: CBViewModel, chatId: String) {
    var reply by rememberSaveable { mutableStateOf("") }

    val onSendReply = {
        if (reply.isNotBlank()) {
            vm.onSendReply(chatId, reply)
            reply = ""
        }
    }

    val chatMessages by vm.chatMessages.collectAsState()
    val myUser by vm.userData.collectAsState()
    val currentChat by vm.chats.collectAsState().find { it.chatId == chatId }

    val chatUser = currentChat?.let { chat -> if (myUser?.userId == chat.user1.userID) chat.user2 else chat.user1 }

    LaunchedEffect(chatId) {
        vm.populateMessages(chatId)
    }

    BackHandler {
        vm.depopulateMessage()
    }

    Column {
        ChatHeader(
            name = chatUser?.name ?: "Unknown",
            imageUrl = chatUser?.imageUrl ?: "",
            onBackClicked = {
                navController.popBackStack()
                vm.depopulateMessage()
            }
        )

        MessageBox(
            modifier = Modifier.weight(1f),
            chatMessages = chatMessages,
            currentUserId = myUser?.userId ?: ""
        )

        ReplyBox(
            reply = reply,
            onReplyChange = { reply = it },
            onSendReply = onSendReply
        )
    }
}

@Composable
fun ChatHeader(name: String, imageUrl: String, onBackClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Rounded.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .clickable { onBackClicked() }
                .padding(8.dp)
        )

        CommonImage(
            data = imageUrl,
            modifier = Modifier
                .padding(8.dp)
                .size(50.dp)
                .clip(CircleShape)
        )

        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
fun MessageBox(modifier: Modifier = Modifier, chatMessages: List<Message>, currentUserId: String) {
    LazyColumn(modifier = modifier) {
        items(chatMessages) { msg ->
            val alignment = if (msg.sentby == currentUserId) Alignment.End else Alignment.Start
            val color = if (msg.sentby == currentUserId) Purple80 else Color.White
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = alignment
            ) {
                Text(
                    text = msg.message ?: "",
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(color)
                        .padding(12.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ReplyBox(reply: String, onReplyChange: (String) -> Unit, onSendReply: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CommonDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = reply,
                onValueChange = onReplyChange,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onSendReply) {
                Text(text = "Send")
            }
        }
    }
}

@Composable
fun CommonDivider() {
    Divider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier
            .alpha(0.3f)
            .padding(top = 8.dp, bottom = 8.dp)
    )
}

@Composable
fun CommonImage(
    data: String,
    modifier: Modifier = Modifier.wrapContentSize(),
    contentScale: ContentScale = ContentScale.Crop
) {
    val painter = rememberImagePainter(data = data)
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
}
