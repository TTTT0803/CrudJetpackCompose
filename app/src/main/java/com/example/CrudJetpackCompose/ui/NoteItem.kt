package com.example.CrudJetpackCompose.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.CrudJetpackCompose.model.Note

@Composable
fun NoteItem(note: Note, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 13.dp)
            .clickable { onClick() }, // 🟢 Click để mở cập nhật
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB)) // Màu nền xanh nhạt
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = note.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.description,
                fontSize = 14.sp,
                color = Color.DarkGray,
                maxLines = 2, // 👈 Giới hạn số dòng hiển thị
                overflow = TextOverflow.Ellipsis // 👈 Hiển thị "..." nếu văn bản quá dài
            )

        }
    }
}
