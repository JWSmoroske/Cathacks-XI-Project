package com.example.navigationtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.navigationtest.screens.QuizScreen
import com.example.navigationtest.screens.InfoScreen
import com.example.navigationtest.screens.QueryScreen
import com.example.navigationtest.ui.theme.NavigationTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationTestTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val currentTitle = remember { mutableStateOf("Cybersecurity Basics") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { SmallTopAppBar(title = currentTitle.value) },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                onTitleChange = { currentTitle.value = it }
            )
        }
    ) { innerPadding ->

        val graph =
            navController.createGraph(startDestination = Screen.Info.rout) {
                composable(route = Screen.Query.rout) {
                    QueryScreen()
                }
                composable(route = Screen.Quiz.rout) {
                    QuizScreen()
                }
                composable(route = Screen.Info.rout) {
                    InfoScreen()
                }
            }
        NavHost(
            navController = navController,
            graph = graph,
            modifier = Modifier.padding(innerPadding)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun TrialPreview() {
    NavigationTestTheme {
        MainScreen()
    }
}
