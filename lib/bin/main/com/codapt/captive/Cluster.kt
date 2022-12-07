package com.codapt.captive

import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import kotlinx.serialization.encodeToString
import kotlinx.serialization.Serializable

class Cluster(path : String, name: String) {

    private var clusterAbsolutePath : String
    private val cluster : File
    private val clusterPath : String
    private var clusterName : String

    // Create cluster directory
    init {
        clusterName = fixName(name)
        clusterAbsolutePath = path
        clusterPath = "$clusterAbsolutePath/$clusterName"
        cluster = File(clusterPath)
        if (!cluster.isDirectory) cluster.mkdir()
        clusterAbsolutePath = cluster.path
    }

    /**Gets full path to cluster
     *
     *
     *  for example the full path for a cluster named users  in the home path would be
     *  the ~/database/cluster
     * **/
    fun getPath() : String = clusterAbsolutePath

    /**returns the name of the cluster**/
    fun getName() : String = clusterName

    fun exists() : Boolean = cluster.exists()

    fun getFiles() : Array<out File>? {
        return cluster.listFiles()
    }

    fun getDocCount() : Int? = getFiles()?.size

    /**
     *Creates a new json document
     * @param docName : [String] the file name excluding the file extension
     * @param serializable : @Serializable data class that will be encoded to json and written to a file
     **/
    inline fun <reified T> addDocument(serializable : T, docName :String)  {

        try {
            val documentString = serialize(serializable)
            val doc = File("${getPath()}/$docName.json")
            if (!doc.exists()) doc.createNewFile()
            doc.writeText(documentString)
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

    fun deleteDocument(docName: String) {
        val document = File("$clusterAbsolutePath/$docName.json")
        if(!document.exists()) throw DocumentDoesNotExist(docName, clusterName)
        else document.delete()
    }

    inline fun <reified T>getDocument(docName: String) : T   {
        val pathName = "${getPath()}/$docName.json"
        val doc = File(pathName)
        val string  = doc.readText()
        return Json.decodeFromString<T>(string) ?: throw SerializationException()
    }

    fun deleteDocuments() {
        cluster.listFiles()?.forEach {
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

    inline fun <reified T>getDocuments() : String {
        val list : @Serializable MutableList<T> = mutableListOf()
        getFiles()?.forEach {
            val doc = getDocument<T>(it.nameWithoutExtension)
            list.add(doc)
        }
        return Json.encodeToString(list)
    }

    /**Returns JSON string of document names passed***/
    inline fun <reified T>getDocuments(docs: List<String>) : String {
        val list : @Serializable MutableList<T> = mutableListOf()

        docs.forEach {
            if (docExists(it)) list.add(getDocument(it))
        }

        return Json.encodeToString(list)
    }

}
