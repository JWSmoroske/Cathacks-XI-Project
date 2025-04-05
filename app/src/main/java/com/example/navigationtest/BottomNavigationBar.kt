package com.example.navigationtest

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    val navigationItems = listOf(
        NavigationItem(
            title = "Overview",
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_description_24),
                    contentDescription = "Overview"
                )
            },
            route = Screen.Overview.rout
        ),
        NavigationItem(
            title = "Practices",
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_info_outline_24),
                    contentDescription = "Practices"
                )
            },
            route = Screen.Practices.rout
        ),
        NavigationItem(
            title = "Ask",
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_help_outline_24),
                    contentDescription = "Ask"
                )
            },
            route = Screen.Query.rout
        )
    )

    NavigationBar(
        containerColor = Color.White
    ) {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.route)
                },
                icon = {
                    item.icon()
                },
                label = {
                    Text(
                        item.title,
                        color = if (index == selectedNavigationIndex.intValue)
                            Color.Black
                        else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.surface,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )

            )
        }
    }
}

data class NavigationItem(
    val title: String,
    val icon: @Composable () -> Unit,
    val route: String
)