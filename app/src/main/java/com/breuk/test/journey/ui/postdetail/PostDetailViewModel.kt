package com.breuk.test.journey.ui.postdetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.breuk.test.journey.domain.model.Comment
import com.breuk.test.journey.domain.model.Post
import com.breuk.test.journey.navigation.ARG_POST_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val postId = savedStateHandle.get<String>(ARG_POST_ID).orEmpty()

    private val viewModelState = mutableStateOf(PostDetailScreenState())
    val state: State<PostDetailScreenState> = viewModelState

    private val viewModelEvent = MutableSharedFlow<PostDetailEvent>()
    val event = viewModelEvent.asSharedFlow()

    init {

    }

    data class PostDetailScreenState(
        val post: Post? = null,
        val comments: List<Comment> = emptyList(),
        val isLoading: Boolean = false
    )

    sealed class PostDetailEvent {
        data class ShowError(val message: String) : PostDetailEvent()
    }
}