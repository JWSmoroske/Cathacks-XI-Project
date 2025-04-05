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
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(
    navController: NavController,
    onTitleChange: (String) -> Unit
) {
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(1)
    }

    val navigationItems = listOf(
        NavigationItem(
            title = "Quiz",
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_quiz_24),
                    contentDescription = "Overview"
                )
            },
            route = Screen.Quiz.rout,
            description = "Quiz Yourself"
        ),
        NavigationItem(
            title = "Info",
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_info_outline_24),
                    contentDescription = "Practices"
                )
            },
            route = Screen.Info.rout,
            description = "Cybersecurity Basics"
        ),
        NavigationItem(
            title = "Ask",
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_help_outline_24),
                    contentDescription = "Ask"
                )
            },
            route = Screen.Query.rout,
            description = "Ask Anything"
        )
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        navigationItems.forEachIndexed { index, item ->
            val isSelected = selectedNavigationIndex.intValue == index
            val textColor = if (isSelected) {
                MaterialTheme.colorScheme.onPrimaryContainer // Selected
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant // Unselected
            }
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.route)
                    onTitleChange(item.description)
                },
                icon = {
                    item.icon()
                },
                label = {
                    Text(
                        item.title,
                        color = textColor
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )

            )
        }
    }
}

data class NavigationItem(
    val title: String,
    val icon: @Composable () -> Unit,
    val route: String,
    val description: String
)