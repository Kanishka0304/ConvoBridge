package com.example.projectlogin

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.example.projectlogin.data.USER_NODE
import com.example.projectlogin.data.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController, vm : CBViewModel) {
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val name = remember { mutableStateOf(TextFieldValue()) } // Username
    val userData = mutableStateOf<user?>(null)

        val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    fun registerUser(name: String, email: String ) {
        val emailValue = email.trim()
        val passwordValue = password.value.trim()
        val confirmPasswordValue = confirmPassword.value.trim()

        if (emailValue.isEmpty() || passwordValue.isEmpty() || confirmPasswordValue.isEmpty() || name.isEmpty()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (passwordValue != confirmPasswordValue) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.createUserWithEmailAndPassword(emailValue, passwordValue)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            vm.signIn.value = true
                            var userId = auth.currentUser?.uid
                            if (userId != null) {
                                val userd = user(
                                    userId = userId,
                                    email = email,
                                    name = name
                                )

                                firestore.collection("users").document(userId)
                                    .set(userd)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                                        // Navigate to login or home screen
                                        navController.navigate("login")
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(context, "Failed to save user data: ${it.message}", Toast.LENGTH_SHORT).show()
                                    }
                                vm.getUserData(userId)
                            }
                        } else {
                            // Registration failed
                            Toast.makeText(context, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF3573CC),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Convo")
                    }
                    append("Bridge")
                },
                fontSize = 28.sp,
                color = Color(0xFFFF6F00), // Light orange
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(bottom = 24.dp)
            )
            Text(
                text = "Register",
                fontSize = 26.sp,
                color = Color(0xFF3573CC), // Blue
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
            Text(
                text = "Please fill in the following details to create an account.",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(bottom = 40.dp)
            )
            TextField(
                value = name.value,
                onValueChange = { name.value =it},
                label = { Text("Username") }, // Changed from Name
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF3573CC),
                    unfocusedIndicatorColor = Color.Gray
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
                    .background(Color(0xFFF1F1F1)) // Light gray background
                    .border(1.dp, Color(0xFFDDDDDD)) // Border color
            )
            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF3573CC),
                    unfocusedIndicatorColor = Color.Gray
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
                    .background(Color(0xFFF1F1F1))
                    .border(1.dp, Color(0xFFDDDDDD))
            )
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF3573CC),
                    unfocusedIndicatorColor = Color.Gray
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
                    .background(Color(0xFFF1F1F1))
                    .border(1.dp, Color(0xFFDDDDDD))
            )
            TextField(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF3573CC),
                    unfocusedIndicatorColor = Color.Gray
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
                    .background(Color(0xFFF1F1F1))
                    .border(1.dp, Color(0xFFDDDDDD))
            )
            Spacer(modifier = Modifier.height(20.dp))

            ElevatedButton(
                onClick = { registerUser(name = name.value.text, email= email.value.text) },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3573CC), // Blue
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text(text = "Sign Up")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(Color.Gray),
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "OR",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(Color.Gray),
                    color = Color.Gray
                )
            }

            ElevatedButton(
                onClick = {},
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF6F00), // Light orange
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text(text = "Sign in with Google")
            }
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Already have an account?",
                    color = Color.Black)

                val annotatedText = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color(0xFF3573CC), textDecoration = TextDecoration.Underline)) {
                        append("Login")
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                val context = LocalContext.current
                ClickableText(
                    text = annotatedText,
                    onClick = {
                        navController.navigate(route = "login")
                        Toast.makeText(context, "Navigating to Login", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}
