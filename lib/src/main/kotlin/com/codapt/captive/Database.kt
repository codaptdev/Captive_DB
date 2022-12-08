package com.codapt.captive

import java.io.File

class Database(name: String, path : String = ".") {
    private val clusters = mutableListOf<String>()
    private val fullPath : String
    val db : File

    init {
        val dbName = formatName(name)
        db = File("$path/$dbName")
        db.mkdir()
        fullPath = db.absolutePath
    }

    fun createCluster(name: String) : Cluster {
        val dbPath = db.path
        val clusterName = formatName(name)
        val cluster = File("$dbPath/$clusterName")
        cluster.mkdir()
        clusters.add(clusterName)
        return getCluster(clusterName)
    }

    fun getCluster(name: String): Cluster {
        val clusterName = formatName(name)
        if (!clusterExist(clusterName)) throw ClusterDoesNotExist(clusterName, "${db.path}\\$clusterName")
        return Cluster(db.path, clusterName)
    }

    private fun clusterExist(name: String) : Boolean {
        var found = false

        db.listFiles()?.forEach {
            if(it.name == name) found = true
        }

        return found
    }

    // TODO: fun getClusterNames() : MutableList<String> = clusters
}
