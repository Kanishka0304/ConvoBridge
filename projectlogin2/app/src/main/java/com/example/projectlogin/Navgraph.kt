package com.example.projectlogin

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sign_up.LoginScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.Route


sealed class DestinationScreen(var route: String){
    object SingleChat : DestinationScreen("singleChat/{chatId}"){
        fun createRoute(id:String) = "singlechat/$id"
    }
}



@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val vm = CBViewModel(auth = FirebaseAuth.getInstance(), db = FirebaseFirestore.getInstance())
    NavHost(navController = navController, startDestination = "splash") {
        composable(route="splash"){
            SplashScreen(navController)
        }
        composable(route = "login") {
            LoginScreen(navController)
        }
        composable(route = "register") {
            RegisterScreen(navController,vm)
        }
        composable(route="home"){
            HomeScreen(navController)
        }
        composable(DestinationScreen.SingleChat.route) {
            val chatId = it.arguments?.getString("chatId")
            chatId?.let {
                UserChatScreen(navController = navController, vm = vm,chatId = chatId)
            }

        }
        composable(route = "Chat_Screen") {
            ChatScreen(navController,vm)
        }
        composable(route = "setting") {
            SettingPage(navController)
        }
        composable(route="edit_profile"){
            ProfileScreen(navController)
        }
        composable(route = "reset") {
            ResetPasswordScreen(navController)
        }
        composable(route = "notification") {
            NotificationSettingsScreen(navController)
        }

    }
}
