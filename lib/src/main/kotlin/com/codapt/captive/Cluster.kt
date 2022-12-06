package com.codapt.captive

import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import kotlinx.serialization.encodeToString

class Cluster(path : String, name: String) {

    var clusterAbsolutePath : String
    var documentCount = 0
    val cluster : File
    private val clusterPath : String
    private var clusterName : String
    private var documentIndexes = mutableListOf<String>()

    // Create cluster directory
    init {
        clusterName = fixName(name)
        clusterAbsolutePath = path
        clusterPath = "$clusterAbsolutePath/$clusterName"
        cluster = File(clusterPath)
        if (!cluster.isDirectory) cluster.mkdir()
        clusterAbsolutePath = cluster.path
    }


    /**
     *Creates a new json document
     * @param docName : [String] the file name excluding the file extension
     * @param serializable : @Serializable data class that will be encoded to json and written to a file
     **/
    inline fun <reified T> addDocument(serializable : T, docName :String)  {

        try {
            val documentString = serialize(serializable)
            val doc = File("$clusterAbsolutePath/$docName.json")
            if (!doc.exists()) doc.createNewFile()
            doc.writeText(documentString)
            addDocumentIndex(docName)
            documentCount++
        }
        catch (e : SerializationException) {
            serializableException()
        }
    }

    inline fun <reified T> serialize(serializable: T): String {
        return Json.encodeToString(serializable)
    }

    fun serializableException() {
        throw IllegalArgumentException("""
                The argument you passed for serializable is not a serializable
                Mark the class as @Serializable or provide the serializer explicitly.
                """.trimIndent())
    }

    fun addDocumentIndex(addedFileName : String) {
        documentIndexes.add(addedFileName)
    }

    private fun removeDocumentIndex(deletedFileName : String) {
        documentIndexes.remove(deletedFileName)
    }

    fun deleteDocument(docName: String) {
        val document = File("$clusterAbsolutePath/$docName.json")
        if(!document.exists()) throw DocumentDoesNotExist(docName, clusterName)
        else document.delete()
        removeDocumentIndex(docName)
    }

    inline fun <reified T>getDocument(docName: String) : T   {
        val pathName = "$clusterAbsolutePath/$docName.json"
        val doc = File(pathName)
        val string  = doc.readText()
        return Json.decodeFromString<T>(string) ?: throw SerializationException()
    }

    fun deleteDocuments() {
        cluster.listFiles().forEach {
            it.delete()
        }
    }
    fun deleteDocuments(documents : List<String>) {
        for(doc in documents){
            val currentDoc = File("${clusterAbsolutePath}/$doc.json")
            currentDoc.delete()
        }
    }

    /**
     * @param : The name of the file excluding the .json extension
     * **/
    fun docExists(name : String) : Boolean {
        val file = File("$clusterAbsolutePath/$name.json")
        return file.exists()
    }

}


