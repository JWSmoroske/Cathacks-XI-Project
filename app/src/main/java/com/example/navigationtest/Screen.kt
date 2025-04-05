package com.example.navigationtest

sealed class Screen(val rout: String) {
    object Quiz: Screen("quiz_screen")
    object Info: Screen("info_screen")
    object Query: Screen("query_screen")
}