package com.solopov.jetpack_compose_practice

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class Screen(val route: String, @StringRes val resourceId: Int, @DrawableRes val drawableId: Int? = null) {
    object SplashScreen: Screen("splash_screen", R.string.splash_screen)
    object MainScreen: Screen("main_screen", R.string.main_screen, R.drawable.ic_home)
    object FavoritesScreen: Screen("favorites_screen", R.string.favorties_screen, R.drawable.ic_heart)
    object DetailsScreen: Screen("details_screen", R.string.details_screen, null)
}
