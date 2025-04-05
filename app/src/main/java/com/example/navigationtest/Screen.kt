package com.example.navigationtest

sealed class Screen(val rout: String) {
    object Overview: Screen("overview_screen")
    object Practices: Screen("practices_screen")
    object Query: Screen("query_screen")
}