package com.example.projectlogin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sign_up.R

@Composable
fun AppBarComponents(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White) // Background color white
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF3573CC), // Blue color for "Convo"
                        fontSize = 30.sp
                    )
                ) {
                    append("Convo")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFFFF6F00), // Orange color for "Bridge"
                        fontSize = 30.sp
                    )
                ) {
                    append("Bridge")
                }
            },
            fontSize = 30.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1f) // Adjusts width to avoid text cut-off
                .padding(end = 16.dp)
        )
        Spacer(modifier = Modifier.size(20.dp))
        IconComponent(
            drawableId = R.drawable.ic_camera,
            modifier = Modifier.clickable { navController.navigate("edit_profile") },
            iconTint = Color(0xFFFF6F00) // Light orange
        )
        Spacer(modifier = Modifier.size(20.dp))
        IconComponent(
            drawableId = R.drawable.ic_search,
            modifier = Modifier.clickable { navController.navigate("login") },
            iconTint = Color(0xFFFF6F00) // Light orange
        )
        Spacer(modifier = Modifier.size(20.dp))
        IconComponent(
            drawableId = R.drawable.ic_menu,
            modifier = Modifier.clickable { navController.navigate("setting") },
            iconTint = Color(0xFFFF6F00) // Light orange
        )
    }
}

@Composable
fun IconComponent(drawableId: Int, modifier: Modifier, iconTint: Color) {
    Icon(
        painter = painterResource(id = drawableId),
        contentDescription = null,
        tint = iconTint, // Dynamic tint
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun AppBarComponentsPreview() {
    // Mock NavController
    val mockNavController = rememberNavController()

    // Preview the AppBar
    AppBarComponents(navController = mockNavController)
}
