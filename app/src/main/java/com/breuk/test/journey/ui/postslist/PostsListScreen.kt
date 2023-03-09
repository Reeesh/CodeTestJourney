package com.breuk.test.journey.ui.postslist

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun PostsListScreen(navController: NavController, viewModel: PostsListViewModel = hiltViewModel()) {
    val state = viewModel.state.value
}