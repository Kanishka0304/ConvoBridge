package com.example.projectlogin


import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectlogin.ui.theme.Blue08
import com.example.sign_up.R
import okhttp3.Route


@Composable
fun AppBarComponents(navController: NavController) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.convobridge_title),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

        )
        Spacer(modifier = Modifier.weight(1f))
        IconComponent(
            R.drawable.ic_camera,
            modifier = Modifier
                .clickable(enabled = true, onClick = {navController.navigate("edit_profile")})

        )
        Spacer(modifier = Modifier.size(20.dp))
        IconComponent(R.drawable.ic_search, modifier = Modifier
            .clickable(enabled = true, onClick = {navController.navigate("login")})
        )
        Spacer(modifier = Modifier.size(20.dp))
        IconComponent(R.drawable.ic_menu, modifier = Modifier
            .clickable(enabled = true, onClick = {navController.navigate("setting")}))
    }
}

@Composable
fun IconComponent(drawableId: Int,modifier: Modifier) {
    Icon(
        painter = painterResource(id = drawableId),
        contentDescription = " ",
        tint = Color.White,
        modifier = modifier
    )

}
//
//@Preview
//@Composable
//fun AppBarComponentPreview() {
//    AppBarComponents()
//}