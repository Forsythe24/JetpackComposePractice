package com.solopov.jetpack_compose_practice

import SplashScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    var bottomBarVisibility by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                visibility = bottomBarVisibility
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.SplashScreen.route) {
                LaunchedEffect(Unit) {
                    bottomBarVisibility = false
                }
                SplashScreen(navController = navController)
            }
            composable(Screen.MainScreen.route) {
                LaunchedEffect(Unit) {
                    bottomBarVisibility = true
                }
                MainScreen(navController = navController)
            }
            composable(
                route = Screen.DetailsScreen.route + "/{place}",
                arguments = listOf(
                    navArgument("place") {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                LaunchedEffect(Unit) {
                    bottomBarVisibility = false
                }
                val place = Gson().fromJson(entry.arguments?.getString("place"), Place::class.java)
                DetailsScreen(
                    navController = navController, place = place
                )
            }
            composable(Screen.FavoritesScreen.route) {
                LaunchedEffect(Unit) {
                    bottomBarVisibility = true
                }
                FavoritesScreen(navController = navController)
            }
        }
    }

}

@Composable
fun BottomBar(navController: NavController, visibility: Boolean) {
    val screens = listOf(
        Screen.MainScreen,
        Screen.FavoritesScreen
    )
    AnimatedVisibility(
        visible = visibility,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        BottomNavigation(
            backgroundColor = Color.White,
            elevation = 0.dp,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route
                screens.forEachIndexed { index, screen ->
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        this@BottomNavigation.BottomNavigationItem(
                            icon = {
                                Icon(
                                    modifier = Modifier
                                        .size(24.dp),
                                    painter = painterResource(id = screen.drawableId!!),
                                    tint = Color(0xFF848282),
                                    contentDescription = null,
                                )
                            },
                            selectedContentColor = Color(0xFF848282),
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (currentRoute == screen.route) {
                            RedCircle()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RedCircle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(8.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color(0xFFFF4A4A),
                style = Fill
            )
        }
    }
}
