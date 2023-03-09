package com.breuk.test.journey.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.breuk.test.journey.ui.postdetail.PostDetailScreen
import com.breuk.test.journey.ui.postslist.PostsListScreen

@Composable
fun MainNavigation(startDestination: Screen = Screen.PostsList) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination.route) {
        composable(Screen.PostsList.route) { PostsListScreen(navController) }
        composable(Screen.PostDetail.route) { PostDetailScreen(navController) }
    }
}