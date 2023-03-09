package com.breuk.test.journey.ui.postslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.breuk.test.journey.R
import com.breuk.test.journey.core.ui.PostListItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PostsListScreen(navController: NavController, viewModel: PostsListViewModel = hiltViewModel()) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is PostsListViewModel.PostsListEvent.ShowError -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.padding(horizontal = 20.dp),
        scaffoldState = scaffoldState
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                value = "",
                onValueChange = {}) //TODO add search

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (state.posts.isEmpty()) {
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            text = stringResource(R.string.no_posts_found),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    itemsIndexed(state.posts) { index, post ->
                        if (index != 0) {
                            Divider(
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.Gray
                            )
                        }
                        PostListItem(
                            post = post,
                            onPostPressed = {
                                //TODO show detail
                            }
                        )
                    }
                }
            }
        }
    }
}