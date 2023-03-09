package com.breuk.test.journey.ui.postslist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breuk.test.journey.core.util.SEARCH_DELAY
import com.breuk.test.journey.core.util.Task
import com.breuk.test.journey.domain.model.Post
import com.breuk.test.journey.domain.usecase.GetPosts
import com.breuk.test.journey.domain.usecase.SearchPosts
import com.breuk.test.journey.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsListViewModel @Inject constructor(
    private val getPosts: GetPosts,
    private val searchPosts: SearchPosts
) : ViewModel() {
    private val viewModelState = mutableStateOf(PostsListScreenState())
    val state: State<PostsListScreenState> = viewModelState

    private val viewModelEvent = MutableSharedFlow<PostsListEvent>()
    val event = viewModelEvent.asSharedFlow()

    private val viewModelSearchText = mutableStateOf("")
    val searchText: State<String> = viewModelSearchText

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

    private var searchQuery: Job? = null
    fun doSearchPosts(text: String) {
        viewModelSearchText.value = text
        searchQuery?.cancel()
        searchQuery = viewModelScope.launch {
            delay(SEARCH_DELAY)
            val searchText = "%$text%"
            searchPosts(searchText).onEach { task ->
                when (task) {
                    is Task.Success -> {
                        viewModelState.value = state.value.copy(
                            posts = task.data ?: emptyList()
                        )
                    }

                    is Task.Loading -> {}
                    is Task.Error -> {}
                }
            }.launchIn(this)
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