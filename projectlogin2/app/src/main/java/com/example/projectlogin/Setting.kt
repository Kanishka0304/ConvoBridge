package com.example.projectlogin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


// Setting page
@Composable
fun SettingPage(navController: NavHostController){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(90.dp)
            .clip(shape = RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp))
    ) {
        IconButton(onClick = {
            navController.navigate("home")
        }, modifier = Modifier
            .padding(20.dp)
        ) {
            Icon(
                Icons.Default.ArrowBack, contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
            )
        }
        Text(
            text = "Settings",
            fontSize = 30.sp,
            textAlign = TextAlign.Right,
            modifier = Modifier
                .background(Color.Transparent)
                .padding(20.dp)
                .align(Alignment.BottomEnd)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Accounts Changes
        Spacer(modifier = Modifier.height(90.dp))
        Text(
            text = "ACCOUNTS",
            fontSize = 25.sp,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp, 20.dp)
        )

        // Edit Profile button
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            ElevatedButton(onClick = {
                navController.navigate("edit_profile")
            }) {
                Icon(
                    Icons.Default.AccountCircle, contentDescription = null,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = "Edit Profile",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(100.dp))
                Icon(
                    Icons.Default.KeyboardArrowRight, contentDescription = null,
                    modifier = Modifier
                        .width(20.dp)
                )
            }
        }

        //  Change Password
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            ElevatedButton(onClick = {
                navController.navigate("reset")
            }){
                Icon(
                    Icons.Default.Lock, contentDescription = null,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = "Change Password",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(40.dp))
                Icon(
                    Icons.Default.KeyboardArrowRight, contentDescription = null,
                    modifier = Modifier
                        .width(20.dp)
                )
            }
        }
        // preferences
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "PREFERENCES",
            fontSize = 25.sp,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp, 20.dp)
        )
        // Language
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            ElevatedButton(onClick = {
            }) {
                Icon(
                    Icons.Default.FavoriteBorder, contentDescription = null,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = "Language",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(100.dp))
                Icon(
                    Icons.Default.KeyboardArrowRight, contentDescription = null,
                    modifier = Modifier
                        .width(20.dp)
                )
            }
        }

        // Notifications
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            ElevatedButton(onClick = {
                navController.navigate("notification")
            }) {
                Icon(
                    Icons.Default.Notifications, contentDescription = null,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = "Notification",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(90.dp))
                Icon(
                    Icons.Default.KeyboardArrowRight, contentDescription = null,
                    modifier = Modifier
                        .width(20.dp)
                )
            }
        }
        //Logout
        Spacer(modifier = Modifier.height(60.dp))
        Button(onClick = {
            navController.navigate("login")
        }) {
            Text(
                text = "Logout",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }
    }
}
