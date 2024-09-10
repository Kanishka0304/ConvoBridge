package com.example.projectlogin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController) {

    val INITIAL_SCREEN_INDEX: Int = 0
    val pagerState = rememberPagerState(pageCount = {3}, initialPage =INITIAL_SCREEN_INDEX)
    val scope = rememberCoroutineScope()

    Column {
        AppBarComponents(navController = navController)
        TabBarComponents(
            initialIndex = INITIAL_SCREEN_INDEX,
            pagerState = pagerState,
            onTabSelected = { selectedPage ->
                scope.launch {
                    pagerState.animateScrollToPage(selectedPage)
                }
            }
        )

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState
        ) { page ->
            when(page){
                0 -> ChatScreen(navController, vm = CBViewModel(auth = FirebaseAuth.getInstance(), db = FirebaseFirestore.getInstance()))
                1 -> StatusScreen()
                2 -> CallsScreen()
            }

        }

    }
}