package com.breuk.test.journey.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.breuk.test.journey.domain.model.Post

@Composable
fun PostListItem(modifier: Modifier = Modifier, post: Post, onPostPressed: (Int) -> Unit) {
    Column(
        modifier = modifier
            .clickable(onClick = { onPostPressed(post.id) })
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
    ) {
        Text(
            modifier = Modifier.padding(vertical = 6.dp),
            text = post.title,
            fontSize = 18.sp,
            color = Color.Black
        )
        Text(
            modifier = Modifier.padding(vertical = 6.dp),
            text = post.body,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}