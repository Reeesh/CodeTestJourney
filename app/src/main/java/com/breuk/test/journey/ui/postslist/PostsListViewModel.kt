package com.breuk.test.journey.ui.postslist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breuk.test.journey.core.util.Task
import com.breuk.test.journey.domain.model.Post
import com.breuk.test.journey.domain.usecase.GetPosts
import com.breuk.test.journey.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostsListViewModel @Inject constructor(
    private val getPosts: GetPosts
) : ViewModel() {
    private val viewModelState = mutableStateOf(PostsListScreenState())
    val state: State<PostsListScreenState> = viewModelState

    private val viewModelEvent = MutableSharedFlow<PostsListEvent>()
    val event = viewModelEvent.asSharedFlow()

    init {
        doGetPosts()
    }

    private fun doGetPosts() {
        viewModelScope.launch {
            getPosts().onEach { task ->
                when (task) {
                    is Task.Success -> {
                        viewModelState.value = state.value.copy(
                            posts = task.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Task.Loading -> {
                        viewModelState.value = state.value.copy(
                            posts = task.data ?: emptyList(),
                            isLoading = true
                        )
                    }

                    is Task.Error -> {
                        viewModelState.value = state.value.copy(
                            posts = task.data ?: emptyList(),
                            isLoading = false
                        )
                        viewModelEvent.emit(
                            PostsListEvent.ShowError(
                                task.exception?.message ?: "Something went wrong, try again"
                            )
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun showDetail(id: Int) {
        viewModelScope.launch {
            viewModelEvent.emit(PostsListEvent.Navigate(Screen.PostDetail.withId(id)))
        }
    }

    data class PostsListScreenState(
        val posts: List<Post> = emptyList(),
        val isLoading: Boolean = false
    )

    sealed class PostsListEvent {
        data class ShowError(val message: String) : PostsListEvent()
        data class Navigate(val route: String) : PostsListEvent()
    }
}