package com.codapt.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Word(
    val word: String,
    val def: String,
    val lang : String
)
