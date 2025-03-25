package com.example.CrudJetpackCompose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.CrudJetpackCompose.model.Note
import com.example.CrudJetpackCompose.repository.FirestoreRepository
import com.example.CrudJetpackCompose.ui.components.CreateNoteDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val repository = remember { FirestoreRepository() }
    var notes by remember { mutableStateOf<List<Note>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    var selectedNote by remember { mutableStateOf<Note?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var showCreateDialog by remember { mutableStateOf(false) }



    LaunchedEffect(Unit) {
        coroutineScope.launch {
            repository.getNotes().collectLatest { notes = it }
        }
    }



    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )

    {



        if (notes.isEmpty()) {
            Text(text = "No Notes Found!", fontSize = 20.sp)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                items(notes) { note ->
                    NoteItem(note, onClick = {
                        selectedNote = note
                        showDialog = true
                    })
                }
            }
        }

        // 🟢 Hiển thị Dialog khi click vào Note
        // Hiển thị Dialog khi click vào Note
        if (showDialog && selectedNote != null) {
            EditNoteDialog(
                note = selectedNote!!,
                onDismiss = { showDialog = false },
                onUpdate = { updatedNote ->
                    repository.updateNote(
                        updatedNote,
                        onSuccess = { showDialog = false },
                        onFailure = { errorMessage -> println("Update failed: $errorMessage") }
                    )
                },
                onDelete = { noteToDelete ->
                    repository.deleteNote(
                        noteToDelete.id,  // 🔹 Chỉ truyền ID của Note, không truyền cả Note
                        onSuccess = { showDialog = false },
                        onFailure = { errorMessage -> println("Delete failed: $errorMessage") }
                    )

                }
            )
        }
        if (showCreateDialog) {
            CreateNoteDialog(
                onDismiss = { showCreateDialog = false },
                onCreate = { newNote ->
                    repository.addNote(
                        newNote,
                        onSuccess = { showCreateDialog = false },
                        onFailure = { errorMessage -> println("Lỗi khi thêm note: $errorMessage") }
                    )

                }
            )
        }
    }

    // 🔵 Floating Action Button (Nút Thêm) ở góc dưới phải
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { showCreateDialog = true }, // 🟢 Mở form khi bấm vào FAB
            modifier = Modifier
                .padding(16.dp)
                .offset(y = (-40).dp) // Đẩy lên một chút
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
        }

    }
}
