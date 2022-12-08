package com.codapt.captive

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class UtilsTest {
    @Test
    fun formatNameTest() {
        assertEquals("user", formatName("user.json"))
        assertEquals("config", formatName("config.json"))
        assertEquals("captive_db", formatName("captive db.json"))
        assertEquals("settings", formatName("settings"))
        assertEquals("settings", formatName(" settings "))
        assertEquals("settings", formatName(" settings  .json"))

    }

    @Test
    fun removeFileExtension() {
        assertEquals("name", removeFileExtension("name.json"))
        assertEquals("name", removeFileExtension("name.json "))
        assertEquals("name", removeFileExtension("name. json "))
        assertEquals("name ", removeFileExtension("name . json "))
    }



}