package com.solopov.jetpack_compose_practice

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object GreetingScreen: Screen("greeting_screen")

    // obviously, this function will do only in the case when you have only mandatory parameters, not mixed with parameters like ?name={name}
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
