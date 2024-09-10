package com.example.sign_up

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectlogin.CBViewModel
import com.example.projectlogin.ChatScreen
import com.example.projectlogin.HomeScreen
import com.example.projectlogin.NavGraph
import com.example.projectlogin.NotificationSettingsScreen
import com.example.projectlogin.ResetPasswordScreen
import com.example.projectlogin.UserChatScreen
import com.google.firebase.auth.FirebaseAuth
import com.example.projectlogin.ui.theme.ProjectloginTheme
import com.google.firebase.firestore.FirebaseFirestore



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectloginTheme {
                val navController = rememberNavController()
                val vm = CBViewModel(auth = FirebaseAuth.getInstance(), db = FirebaseFirestore.getInstance())
                NavGraph()
            }
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current // Get context once
    val isLoading = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF3573CC),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("Convo")
                }
                append("Bridge")
            },
            fontSize = 30.sp,
            color = Color(0xFFFF6F00), // Light orange
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(alignment = Alignment.Start)
        )
        Text(
            text = "Login",
            fontSize = 30.sp,
            color = Color(0xFF3573CC), // Blue
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.Start)
        )
        Text(
            text = "Please enter your Email and Password to login into your account.",
            fontSize = 20.sp,
            color = Color.Gray,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(bottom = 80.dp)
                .align(alignment = Alignment.Start)
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
                .padding(bottom = 20.dp)
                .fillMaxWidth()
                .background(Color(0xFFF1F1F1)) // Light gray background
                .border(1.dp, Color(0xFFDDDDDD)) // Border color
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
                .padding(bottom = 20.dp)
                .fillMaxWidth()
                .background(Color(0xFFF1F1F1))
                .border(1.dp, Color(0xFFDDDDDD))
        )

        val forgotPasswordText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color(0xFF3573CC), textDecoration = TextDecoration.Underline)) {
                append("Forgot Password?")
            }
        }

        ClickableText(
            text = forgotPasswordText,
            onClick = {
                val emailAddress = email.value
                if (emailAddress.isNotEmpty()) {
                    auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Password reset email sent", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Failed to send reset email: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Please enter your email address", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))

        ElevatedButton(
            onClick = {
                isLoading.value = true
                auth.signInWithEmailAndPassword(email.value, password.value)
                    .addOnCompleteListener { task ->
                        isLoading.value = false
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                            navController.navigate("home") // Navigate to Settings page on successful login
                        } else {
                            Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3573CC), // Blue
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(8.dp)
        ) {
            if (isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text(text = "Login")
            }
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

        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Don't have an account?",
                color = Color.Black)

            val registerText = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color(0xFF3573CC), textDecoration = TextDecoration.Underline)) {
                    append("Register")
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            ClickableText(
                text = registerText,
                onClick = {
                    navController.navigate("register")
                    Toast.makeText(context, "Navigating to Register", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
