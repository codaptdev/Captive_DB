package com.codapt.captive

class ClusterDoesNotExist(clusterName : String, path : String)
    : Throwable("Cluster, $clusterName does not exists at the path, $path")

class DocumentDoesNotExist(name : String, clusterName : String)
    : Throwable("Document, $name does not exist in the cluster, $clusterName")