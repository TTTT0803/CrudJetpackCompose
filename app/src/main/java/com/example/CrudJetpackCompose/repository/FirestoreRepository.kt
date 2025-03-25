package com.example.CrudJetpackCompose.repository

import com.example.CrudJetpackCompose.model.Note
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()
    private val notesCollection = db.collection("notes") // Tên collection là "notes"

    // 🟢 Lấy danh sách Notes (Read)
    fun getNotes(): Flow<List<Note>> = callbackFlow {
        val listener = notesCollection.addSnapshotListener { value, error ->
            if (error != null) {
                close(error) // Đóng Flow nếu có lỗi
                return@addSnapshotListener
            }
            val notes = value?.documents?.mapNotNull { it.toObject(Note::class.java)?.copy(id = it.id) }
            trySend(notes ?: emptyList())
        }
        awaitClose { listener.remove() }
    }

    // 🟢 Thêm Note mới (Create)
    fun addNote(note: Note, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        notesCollection.add(note)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    // 🔵 Cập nhật Note (Update)
    fun updateNote(note: Note, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        if (note.id.isNotEmpty()) {
            notesCollection.document(note.id).set(note)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onFailure(it) }
        } else {
            onFailure(Exception("ID không hợp lệ!"))
        }
    }

    // 🔴 Xóa Note (Delete)
    fun deleteNote(noteId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        if (noteId.isNotEmpty()) {
            notesCollection.document(noteId).delete()
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onFailure(it) }
        } else {
            onFailure(Exception("ID không hợp lệ!"))
        }
    }
}
