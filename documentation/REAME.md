# Official Captive Docs

## Topics

- [Adding dependencies](#setting-up-dependencies)
- [Creating a database](#creating-a-database)
- [Creating a cluster](#creating-a-cluster)

## Setting up Dependencies

Note that the following plugin and library is used by the
Captive Library. This is however unnecessary if you got this library from Maven Central

Add the Kotlin Serialization Library

```` Kotlin
implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
````

Add Plugin

```` Kotlin
kotlin("plugin.serialization") version "1.7.21"
````

## Creating a database

```kotlin
val db = Database(name = "my database", path = "~/")
```

- the param name refers to the name of the directory the database will be created in
- the param path refers to the path in which the  database directory will be created
This database will be created at the path ```~/my_database```. Also
note that Captive will remove spaces for you in between words in the
name of your database for uniformity

## Creating a cluster

```kotlin
val users = db.createCluster(name = "users")
// returns an instance of a cluster to the val users 
```
