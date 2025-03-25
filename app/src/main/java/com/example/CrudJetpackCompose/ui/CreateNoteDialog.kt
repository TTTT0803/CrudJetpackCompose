package com.example.CrudJetpackCompose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.CrudJetpackCompose.model.Note

@Composable
fun CreateNoteDialog(
    onDismiss: () -> Unit,
    onCreate: (Note) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") } // Đổi content thành description
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Create Note") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth() // Tăng chiều rộng
                    .height(250.dp) // Tăng chiều cao
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth() // Chiếm toàn bộ chiều rộng
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp), // Tăng chiều cao của ô nhập
                    maxLines = 5 // Tăng số dòng hiển thị
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (title.isNotBlank() && description.isNotBlank()) {
                    onCreate(Note(title = title, description = description)) // Cập nhật Note
                    onDismiss()
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
