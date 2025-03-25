package com.example.CrudJetpackCompose.model

import androidx.annotation.Keep

@Keep
data class Note(
    val id: String = "",
    val title: String = "",
    val description: String = ""
)
