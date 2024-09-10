package com.example.projectlogin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun NotificationSettingsScreen(navController: NavController) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(90.dp)
            .clip(shape = RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp))
    ) {
        IconButton(
            onClick = {
                navController.navigate("setting")
            }, modifier = Modifier
                .padding(20.dp)
        ) {
            Icon(
                Icons.Default.ArrowBack, contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
            )
        }
    }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Manage Notification Settings",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { openNotificationSettings(context) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Open Notification Settings")
            }
        }
    }

fun openNotificationSettings(context: Context) {
    val intent = Intent().apply {
        action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    }
    context.startActivity(intent)
}