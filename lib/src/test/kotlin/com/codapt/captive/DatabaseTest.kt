package com.codapt.captive

import com.codapt.data.models.User
import com.codapt.data.models.Word
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

const val parentPath : String = "C:/Users/tadiw/dev/codapt/captive.kt/lib/TestDBS"

internal class CaptiveTest {

    @Test
    fun initializesDatabase() {
        val db = Database("test db", parentPath)
        assertEquals(true, db.db.exists() )
    }

    @Test
    fun initializesCluster() {
        val db = Database("test db", parentPath)
        val testCluster = db.createCluster("test cluster")
        assertEquals(true, testCluster.exists())
    }

    @Test
    fun addsDocument() {
        val db = Database("test db", parentPath)
        val testCluster = db.createCluster("test cluster")
        val user = User("Tadiwanashe", 17, 'M')
        testCluster.addDocument(user, user.name)
        val file = File("$parentPath/test_db/test_cluster/Tadiwanashe.json")
        assertEquals(true, file.exists())
    }

    @Test
    fun writesJSONToDocument() {
        val db = Database("test db", parentPath)
        val testCluster = db.createCluster("test cluster")
        val user = User("Joe", 34, 'M')
        testCluster.addDocument(user, user.name)
        val file = File("$parentPath/test_db/test_cluster/Joe.json")

        val expected = """{"name":"Joe","age":34,"gender":"M"}""".trimIndent()
        val result = file.readText()

        assertEquals(expected, result)
    }

    @Test
    fun deletesDocument() {
        val db = Database("test db", parentPath)
        val testCluster = db.createCluster("test cluster")
        val user = User("Tadiwa", 25, 'M')
        testCluster.addDocument(user, user.name)
        val file = File("$parentPath/test_db/test_cluster/Tadiwa.json")

        // Test if file was created in the first place
        assertEquals(true, file.exists(), "Test if file was created in the first place")

        // If file was deleted
        testCluster.deleteDocument("Tadiwa")
        assertEquals(false, file.exists(), "If file was deleted")

    }

    @Test
    fun deletesAllDocuments() {
        val db = Database("test db", parentPath)
        val testCluster = db.createCluster("test cluster")

        testCluster.addDocument(User("Tadiwa", 25, 'M'), "Tadiwa")
        testCluster.addDocument(User("Marvin", 16, 'M'), "Marvin")
        testCluster.addDocument(User("Kudzai", 23, 'M'), "Kudzai")
        testCluster.addDocument(User("Tawana", 17, 'M'), "Tawana")
        testCluster.addDocument(User("Tyrik", 15, 'M'), "Tyrik")

        testCluster.deleteDocuments()
        val count = testCluster.getDocCount()
        assertEquals(0, count, message = "Number of users remaining")

    }


    @Test
    fun deletesSelectedDocuments() {
        val db = Database("test db", parentPath)
        val testCluster = db.createCluster("test cluster")

        testCluster.addDocument(User("Tadiwa", 25, 'M'), "Tadiwa")
        testCluster.addDocument(User("Marvin", 16, 'M'), "Marvin")
        testCluster.addDocument(User("Kudzai", 23, 'M'), "Kudzai")
        testCluster.addDocument(User("Tawana", 17, 'M'), "Tawana")
        testCluster.addDocument(User("Tyrik", 15, 'M'), "Tyrik")

        val toBeDeleted = listOf("Tadiwa", "Marvin", "Tyrik")

        testCluster.deleteDocuments(toBeDeleted)

        assertEquals(false, testCluster.docExists("Tadiwa"))
        assertEquals(false, testCluster.docExists("Marvin"))
        assertEquals(false, testCluster.docExists("Tyrik"))

    }

    @Test
    fun getsDocument() {
        val dbPath = "C:/Users/tadiw/dev/codapt/captive.kt/lib/TestDBS"
        val db = Database("words", dbPath)
        val cluster = db.getCluster("english")
        val doc = cluster.getDocument<Word>("happy")

        assertEquals("happy", doc.word)
        assertEquals("en", doc.lang)
        assertEquals("an emotion of joy", doc.def)
    }

//    TODO: @Test fun addsDocuments() {}
//
    @Test
    fun getsDocuments() {
        val dbPath = "C:/Users/tadiw/dev/codapt/captive.kt/lib/TestDBS"
        val db = Database("words", dbPath)
        val cluster = db.getCluster("english")

        val expected = "[{\"word\":\"api\",\"def\":\"a point at which a user can interact with a computer system without being exposed to its complexity\",\"lang\":\"en\"},{\"word\":\"happy\",\"def\":\"an emotion of joy\",\"lang\":\"en\"},{\"word\":\"sad\",\"def\":\"an gloomy emotion\",\"lang\":\"en\"}]"
        val result = cluster.getDocuments<Word>()
        assertEquals(expected, result)
    }

    @Test
    fun getsSelectedDocuments() {
        val expected = "[{\"word\":\"happy\",\"def\":\"an emotion of joy\",\"lang\":\"en\"},{\"word\":\"sad\",\"def\":\"an gloomy emotion\",\"lang\":\"en\"}]"
        val dbPath = "C:/Users/tadiw/dev/codapt/captive.kt/lib/TestDBS"
        val db = Database("words", dbPath)
        val cluster = db.getCluster("english")
        val actual = cluster.getDocuments<Word>(listOf("happy", "sad"))

        assertEquals(expected, actual)
    }

}
