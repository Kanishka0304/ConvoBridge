package com.example.projectlogin

import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import androidx.activity.compose.rememberLauncherForActivityResult as rememberLauncherForActivityResult1
import androidx.compose.material3.Text as Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.projectlogin.data.Profile
import com.example.sign_up.R



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val notification = rememberSaveable { mutableStateOf("") }
    if (notification.value.isNotEmpty()) {
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value = ""
    }

    var name by rememberSaveable { mutableStateOf("default name") }
    var username by rememberSaveable { mutableStateOf("default username") }
    var bio by rememberSaveable { mutableStateOf("default bio") }
    var imageUri by rememberSaveable { mutableStateOf("") }

    // Load profile data from Firestore
    LaunchedEffect(Unit) {
        loadProfileData { profile ->
            name = profile.name
            username = profile.username
            bio = profile.bio
            imageUri = profile.imageUri
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Cancel",
                modifier = Modifier
                    .clickable {
                        notification.value = "Cancelled"
                        navController.navigate("setting")
                    }
                    .padding(5.dp)
            )
            Text(
                text = "Save",
                modifier = Modifier
                    .clickable {
                        saveProfileData(name, username, bio, imageUri)
                        notification.value = "Profile Updated"
                        navController.navigate("setting")

                    }
                    .padding(5.dp)
            )
        }
        ProfileImage(imageUri) { uri ->
            imageUri = uri.toString()
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Name",
                color = Color(0xFF3573CC),
                modifier = Modifier.width(100.dp)
            )
            TextField(
                value = name, onValueChange = { name = it },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedTextColor = Color.Black
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Username",
                color = Color(0xFF3573CC),
                modifier = Modifier.width(100.dp)
            )
            TextField(
                value = username, onValueChange = { username = it },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedTextColor = Color.Black
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Bio",
                color = Color(0xFF3573CC),
                modifier = Modifier
                    .width(100.dp)
                    .padding(top = 8.dp)
            )
            TextField(
                value = bio, onValueChange = { bio = it },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedTextColor = Color.Black
                ),
                singleLine = false,
                modifier = Modifier.height(150.dp)
            )
        }
    }
}

@Composable
fun ProfileImage(imageUri: String, onImageUriChanged: (Uri) -> Unit) {
    val painter = rememberImagePainter(
        if (imageUri.isEmpty()) R.drawable.ic_user else imageUri
    )
    val launcher = rememberLauncherForActivityResult1(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            onImageUriChanged(it)
            uploadImageToFirebase(it)
        }
    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { launcher.launch("image/*") },
                contentScale = ContentScale.Crop
            )
        }
        Text(text = "Change profile picture")
    }
}

fun uploadImageToFirebase(uri: Uri) {
    val storageRef = FirebaseStorage.getInstance().reference
    val profileImagesRef = storageRef.child("profile_images/${uri.lastPathSegment}")
    profileImagesRef.putFile(uri)
        .addOnSuccessListener {
            // Handle successful upload
        }
        .addOnFailureListener {
            // Handle failed upload
        }
}

fun saveProfileData(name: String, username: String, bio: String, imageUri: String) {
    val db = FirebaseFirestore.getInstance()
    val profile = Profile(name, username, bio, imageUri)
    db.collection("profiles").document("user_id") // Replace with the actual user ID
        .set(profile)
        .addOnSuccessListener {
            // Handle successful save
        }
        .addOnFailureListener {
            // Handle failed save
        }
}

fun loadProfileData(onProfileLoaded: (Profile) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("profiles").document("user_id") // Replace with the actual user ID
        .get()
        .addOnSuccessListener { document ->
            if (document != null) {
                val profile = document.toObject(Profile::class.java)
                profile?.let { onProfileLoaded(it) }
            }
        }
        .addOnFailureListener {
            // Handle failed load
        }
}