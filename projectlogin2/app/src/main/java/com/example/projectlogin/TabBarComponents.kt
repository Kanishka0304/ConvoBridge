@file:OptIn(ExperimentalFoundationApi::class)
package com.example.projectlogin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectlogin.data.tabs
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabBarComponents(
    initialIndex: Int = 0,
    pagerState: PagerState,
    onTabSelected: (Int) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(initialIndex) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collectLatest { page ->
            selectedIndex = page
            onTabSelected(selectedIndex)
        }
    }

    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color(0xFFFFFFFF), // Updated to blue theme color
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                color = Color(0xFFFE6F61), // Updated to light orange
                height = 4.dp
            )
        }
    ) {
        tabs.forEachIndexed { index, tabData ->
            androidx.compose.material3.Tab(
                selected = index == selectedIndex,
                onClick = {
                    selectedIndex = index
                    onTabSelected(selectedIndex)
                },
                text = {
                    Text(
                        text = tabData.title,
                        color = if (index == selectedIndex) Color(0xFFFE6F61) else Color.Black, // Light orange for selected tab
                        fontSize = 16.sp
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TabBarComponentsPreview() {
    // Mock pager state for preview
    val pagerState = rememberPagerState(pageCount = { tabs.size }) // Update to reflect the new number of tabs

    TabBarComponents(
        pagerState = pagerState,
        onTabSelected = {}
    )
}
