package com.breuk.test.journey.navigation

const val ARG_POST_ID = "arg_post_id"

sealed class Screen(val route: String) {
    object PostsList : Screen("posts_list_screen")
    object PostDetail : Screen("post_detail_screen/{$ARG_POST_ID}") {
        fun withId(id: Int) = "post_detail_screen/$id"
    }
}