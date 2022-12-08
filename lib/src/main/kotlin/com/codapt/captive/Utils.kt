package com.codapt.captive

/**
 * Formats a given file or folder name string by removing spaces
 * and file extensions
 *
 * For example:
 *
 *      formatName("db config.json") -> "db_config"
 *
 *      formatName("name.json") -> "name"
 *
 *      formatName("user 1") -> "user_1"
 *
 * **/

fun formatName(fileName:String) : String {
    return removeSpaces( removeFileExtension(fileName))
}

fun removeFileExtension(name: String) : String {
    val nameWithExt : List<String> = name.split(".")
    val mutableListOfNameWithExt = nameWithExt.toMutableList()
    return mutableListOfNameWithExt[0]
}

private fun removeSpaces(name: String)  : String{
    val noWhiteSpace = name.trim()

    val splitName : MutableList<String> = noWhiteSpace.split("").toMutableList()
    for (i in splitName.indices) {
        if(splitName[i] == " ") splitName[i] = "_"
    }
    return buildString(splitName)
}

private fun buildString(string : MutableList<String>) : String{
    var result = " ".trim()
    for (part in string) result+= part
    return result
}

