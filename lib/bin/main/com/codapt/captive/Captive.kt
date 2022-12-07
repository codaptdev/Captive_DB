package com.codapt.captive

fun fixName(name : String) : String {
    var count = 0
    val nameWithSpaces = name.split(" ")
    var nameWithUnderscores = ""

    nameWithSpaces.forEach{
        nameWithUnderscores += when(count) {0 -> it else -> "_$it"}
        count++
    }

    return nameWithUnderscores
}