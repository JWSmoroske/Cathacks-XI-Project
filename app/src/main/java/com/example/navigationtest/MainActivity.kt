package com.example.navigationtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.navigationtest.screens.BakingScreen
import com.example.navigationtest.screens.OverviewScreen
import com.example.navigationtest.screens.PracticesScreen
import com.example.navigationtest.screens.QuizScreen
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

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->

        val graph =
            navController.createGraph(startDestination = Screen.Overview.rout) {
                composable(route = Screen.Query.rout) {
                    BakingScreen()
                }
                composable(route = Screen.Overview.rout) {
                    QuizScreen()
                }
                composable(route = Screen.Practices.rout) {
                    PracticesScreen()
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