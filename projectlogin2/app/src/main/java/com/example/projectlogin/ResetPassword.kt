package com.example.projectlogin

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ResetPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() }

    IconButton(onClick = {
        navController.navigate("setting")
    }) {
        Icon(Icons.Rounded.ArrowBack, contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(30.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Reset Password", fontSize = 24.sp, modifier = Modifier.padding(bottom = 24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (newPassword == confirmPassword) {
                    auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Password reset email sent!", Toast.LENGTH_SHORT).show()
                                navController.navigate("setting")
                            } else {
                                Toast.makeText(context, "Failed to send reset email: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reset Password")
        }
    }
}












































//import android.widget.Toast
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material3.Button
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//
//
//@Composable
//fun ResetPassword(navController: NavHostController, modifier: Modifier = Modifier) {
//    var oldpassword by remember {
//        mutableStateOf("")
//    }
//    var newpassword by remember {
//        mutableStateOf("")
//    }
//    var confpassword by remember {
//        mutableStateOf("")
//    }
//    val password = remember { mutableStateOf("") }
//    val context = LocalContext.current // Get context once
//
//    Column(
//        modifier = Modifier
//    ){
//        // back arrow
//        Spacer(modifier = Modifier.height(40.dp))
//        Row {
//            IconButton(onClick = {
//                navController.navigate("setting")
//            }) {
//                Icon(
//                    Icons.Default.ArrowBack, contentDescription = null,
//                    modifier = Modifier
//                        .size(40.dp)
//                )
//            }
//            Spacer(modifier = Modifier.height(40.dp))
//        }
//
//        //icon and text
//        Spacer(modifier = Modifier.height(20.dp))
//        Column(
//            modifier = Modifier
//                .fillMaxWidth(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Icon(
//                Icons.Default.Lock, contentDescription = null,
//                modifier = Modifier
//                    .size(90.dp)
//            )
//            Spacer(modifier = Modifier.height(20.dp))
//            Text(
//                text = "Reset Your Password",
//                fontSize = 20.sp,
//            )
//        }
//        // input password
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        // Input old password
//        OutlinedTextField(value = oldpassword,
//            onValueChange = {oldpassword = it},
//            label = { Text("Old Password") },
//            visualTransformation = PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(5.dp, 0.dp, 10.dp, 5.dp)
//        )
//
//        //Input new password
//        OutlinedTextField(value = newpassword,
//            onValueChange = {newpassword = it},
//            label = { Text("New Password") },
//            visualTransformation = PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(5.dp, 0.dp, 10.dp, 5.dp)
//        )
//
//        //Input confpassword
//        OutlinedTextField(value = confpassword,
//            onValueChange = {confpassword = it},
//            label = { Text("Confirm Password") },
//            visualTransformation = PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(5.dp, 0.dp, 10.dp, 5.dp)
//        )
//        Spacer(modifier = Modifier.height(40.dp))
//
//        Button(onClick = {
//            if (newpassword == confpassword) {
//                password.value = newpassword
//            }
//            else{
//                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
//            }
//        },
//            modifier = Modifier.padding(10.dp)
//        ){
//            Text(
//                text = "Reset Password",
//                fontSize = 20.sp,
//                textAlign = TextAlign.Center,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.CenterVertically)
//                    .padding(10.dp)
//            )
//        }
//    }
//}
