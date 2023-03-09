package com.breuk.test.journey.ui.postdetail.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.breuk.test.journey.domain.model.Comment

@Composable
fun CommentsListItem(modifier: Modifier = Modifier, comment: Comment) {
    Card(
        modifier = modifier.padding(horizontal = 12.dp, vertical = 10.dp),
        shape = RoundedCornerShape(12.dp), elevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp)
        ) {
            Text(
                modifier = Modifier.padding(vertical = 6.dp),
                text = comment.name,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(vertical = 6.dp),
                text = comment.body,
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                modifier = Modifier.padding(vertical = 3.dp),
                text = "Posted by ${comment.email}",
                fontSize = 10.sp,
                color = Color.Gray
            )
        }
    }

}
