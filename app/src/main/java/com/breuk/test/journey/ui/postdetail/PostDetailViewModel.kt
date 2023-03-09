package com.breuk.test.journey.ui.postdetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breuk.test.journey.core.util.Task
import com.breuk.test.journey.domain.model.Comment
import com.breuk.test.journey.domain.model.Post
import com.breuk.test.journey.domain.usecase.GetComments
import com.breuk.test.journey.domain.usecase.GetPost
import com.breuk.test.journey.navigation.ARG_POST_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getComments: GetComments,
    private val getPost: GetPost
) : ViewModel() {
    private val postId = savedStateHandle.get<String>(ARG_POST_ID).orEmpty()

    private val viewModelState = mutableStateOf(PostDetailScreenState())
    val state: State<PostDetailScreenState> = viewModelState

    private val viewModelEvent = MutableSharedFlow<PostDetailEvent>()
    val event = viewModelEvent.asSharedFlow()

    init {
        parsePostId()
    }

    private fun parsePostId() =
        runCatching {
            postId.toInt()
        }.onSuccess { id ->
            viewModelScope.launch {
                launch { doGetPost(id) }
                launch { doGetComments(id) }
            }
        }.onFailure { error ->
            viewModelScope.launch {
                viewModelEvent.emit(
                    PostDetailEvent.ShowError(
                        error.message ?: "Something went wrong, try again"
                    )
                )
            }
        }


    private fun doGetPost(id: Int) {
        viewModelScope.launch {
            getPost(id).onEach { task ->
                when (task) {
                    is Task.Success -> {
                        viewModelState.value = state.value.copy(
                            post = task.data,
                            isLoadingPost = false
                        )
                    }

                    is Task.Loading -> {
                        viewModelState.value = state.value.copy(
                            post = task.data,
                            isLoadingPost = true
                        )
                    }

                    is Task.Error -> {
                        viewModelState.value = state.value.copy(
                            post = task.data,
                            isLoadingPost = false
                        )
                        viewModelEvent.emit(
                            PostDetailEvent.ShowError(
                                task.exception?.message ?: "Something went wrong, try again"
                            )
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    private fun doGetComments(id: Int) {
        viewModelScope.launch {
            getComments(id).onEach { task ->
                when (task) {
                    is Task.Success -> {
                        viewModelState.value = state.value.copy(
                            comments = task.data ?: emptyList(),
                            isLoadingComments = false
                        )
                    }

                    is Task.Loading -> {
                        viewModelState.value = state.value.copy(
                            comments = task.data ?: emptyList(),
                            isLoadingComments = true
                        )
                    }

                    is Task.Error -> {
                        viewModelState.value = state.value.copy(
                            comments = task.data ?: emptyList(),
                            isLoadingComments = false
                        )
                        viewModelEvent.emit(
                            PostDetailEvent.ShowError(
                                task.exception?.message ?: "Something went wrong, try again"
                            )
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    data class PostDetailScreenState(
        val post: Post? = null,
        val comments: List<Comment> = emptyList(),
        val isLoadingComments: Boolean = false,
        val isLoadingPost: Boolean = false
    )

    sealed class PostDetailEvent {
        data class ShowError(val message: String) : PostDetailEvent()
    }
}