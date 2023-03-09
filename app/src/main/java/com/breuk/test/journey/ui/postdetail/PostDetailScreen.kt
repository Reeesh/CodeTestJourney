package com.breuk.test.journey.ui.postdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.breuk.test.journey.R
import com.breuk.test.journey.core.ui.LoadingIndicator
import com.breuk.test.journey.core.ui.PostListItem
import com.breuk.test.journey.ui.postdetail.compose.CommentsListItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PostDetailScreen(
    navController: NavController,
    viewModel: PostDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is PostDetailViewModel.PostDetailEvent.ShowError -> {
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
            if (state.isLoadingPost) {
                LoadingIndicator()
            } else {
                state.post?.let {
                    PostListItem(
                        post = it,
                        onPostPressed = {}
                    )
                }
            }

            if (state.isLoadingComments) {
                LoadingIndicator()
            } else {
                LazyColumn(modifier = Modifier.weight(0.3f)) {
                    if (state.comments.isEmpty()) {
                        item {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp),
                                text = stringResource(R.string.no_comments_found),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        itemsIndexed(state.comments) { index, comment ->
                            CommentsListItem(comment = comment)
                        }
                    }
                }
            }
        }
    }
}