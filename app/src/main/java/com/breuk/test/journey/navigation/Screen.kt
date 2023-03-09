package com.breuk.test.journey.navigation

sealed class Screen(val route: String) {
    object PostsList : Screen("posts_list_screen")
    object PostsListDetail : Screen("posts_list_detail_screen")
}