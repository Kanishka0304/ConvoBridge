@file:OptIn(ExperimentalFoundationApi::class)
package com.example.projectlogin
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectlogin.data.TabData
import com.example.projectlogin.data.tabs
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabBarComponents(
    initialIndex : Int = 0,
    pagerState: PagerState,
    onTabSelected : (Int) -> Unit
) {

    var selectedIndex by remember {
        mutableStateOf(initialIndex)
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collectLatest { page ->
            selectedIndex = page
            onTabSelected(selectedIndex)
        }
    }

    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White,
        indicator = { tabPosition ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPosition[selectedIndex]),
                color = Color.Gray,
                height = 4.dp
            )
        }
    ) {
        tabs.forEachIndexed{index, tabData ->
            androidx.compose.material3.Tab(
                selected = index == selectedIndex,
                onClick = {
                    selectedIndex = index
                    onTabSelected(selectedIndex)
                },
                text = {
                    TabContext(tabData = tabData)
                }
            )
        }
    }
}

@Composable
fun TabContext(tabData: TabData) {
    if(tabData.unreadCount == null)
    {
        TabTitle(title = tabData.title)
    }else{
        TabWithUnreadCount(tabData = tabData)
    }
}
@Composable
fun TabTitle(title: String){
    Text(
        text = title,
        style = TextStyle(
            fontSize = 16.sp
        )
    )
}

@Composable
fun TabWithUnreadCount(tabData: TabData){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        TabTitle(title = tabData.title)

        tabData?.unreadCount?.also { unreadCount ->
            Text(
                text = unreadCount.toString(),
                modifier = Modifier
                    .padding(4.dp)
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
            )
        }
    }
}
//@Preview
//@Composable
//fun TabBarComponentsPreview(){
//    val INITIAL_SCREEN_INDEX
//    val pagerState = rememberPagerState(pageCount = {3}, initialPage = INITIAL_SCREEN_INDEX )
//
//    TabBarComponents(
//        pagerState = pagerState,
//        onTabSelected = {}
//    )
//}
























//
//import android.icu.text.CaseMap.Title
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Tab
//import androidx.compose.material3.TabRow
//import androidx.compose.material3.TabRowDefaults
//import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.key.Key.Companion.Tab
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.whatsappui.data.TabData
//import com.example.whatsappui.data.Tabs
//import com.example.whatsappui.data.tabs
//
//@Composable
//fun TabBarComponents() {
//
//    var selectedIndex by remember {
//        mutableStateOf(0)
//    }
//    TabRow(
//        selectedTabIndex = selectedIndex,
//        modifier = Modifier.fillMaxWidth(),
//        containerColor = MaterialTheme.colorScheme.primary,
//        contentColor = Color.White,
//        indicator = { tabPosition ->
//            TabRowDefaults.Indicator(
//                modifier = Modifier.tabIndicatorOffset(tabPosition[selectedIndex]),
//                color = MaterialTheme.colorScheme.tertiary,
//                height = 4.dp
//            )
//        }
//    ) {
//        tabs.forEachIndexed{index, tabData ->
//            androidx.compose.material3.Tab(
//                selected = index == selectedIndex,
//                onClick = {
//                    selectedIndex = index
//                },
//                text = {
//                    TabContext(tabData = tabData)
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun TabContext(tabData: TabData) {
//    if(tabData.unreadCount == null)
//    {
//        TabTitle(title = tabData.title)
//    }else{
//        TabWithUnreadCount(tabData = tabData)
//    }
//}
//@Composable
//fun TabTitle(title: String){
//    Text(
//        text = title,
//        style = TextStyle(
//            fontSize = 16.sp
//        )
//    )
//}
//
//@Composable
//fun TabWithUnreadCount(tabData: TabData){
//    Row (
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
//    ){
//        TabTitle(title = tabData.title)
//
//        tabData?.unreadCount?.also { unreadCount ->
//            Text(
//                text = unreadCount.toString(),
//                modifier = Modifier
//                    .padding(4.dp)
//                    .size(16.dp)
//                    .clip(CircleShape)
//                    .background(MaterialTheme.colorScheme.background),
//                style = TextStyle(
//                    color = MaterialTheme.colorScheme.primary,
//                    textAlign = TextAlign.Center,
//                    fontSize = 12.sp
//                )
//            )
//        }
//    }
//}
//@Preview
//@Composable
//fun TabBarComponentsPreview(){
//    TabBarComponents()
//}