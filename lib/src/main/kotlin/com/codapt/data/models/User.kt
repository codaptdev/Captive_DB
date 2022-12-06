package com.codapt.data.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name : String,
    val age: Int,
    val gender : Char
)
