package com.codapt.captive

import java.io.File

class Database(name: String, path : String = ".") {
    private val clusters = mutableListOf<String>()
    private val fullPath : String
    val db : File

    init {
        val dbName = fixName(name)
        db = File("$path/$dbName")
        db.mkdir()
        fullPath = db.absolutePath
    }

    fun createCluster(name: String) : Cluster {
        val dbPath = db.path
        val clusterName = fixName(name)
        val cluster = File("$dbPath/$clusterName")
        cluster.mkdir()
        clusters.add(clusterName)
        return getCluster(clusterName)
    }

    fun getCluster(name: String): Cluster {
        val clusterName = fixName(name)
        if (!clusterExist(clusterName)) throw ClusterDoesNotExist(clusterName, "${db.path}\\$clusterName")
        return Cluster(db.path, clusterName)
    }

    private fun clusterExist(nameWithoutExtension: String) : Boolean {
        var found = false

        db.listFiles()?.forEach {
            if(it.nameWithoutExtension == nameWithoutExtension) found = true
        }

        return found
    }

    // TODO: fun getClusterNames() : MutableList<String> = clusters
}
