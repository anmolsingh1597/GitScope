package com.example.gitscope.presentation.util.extensions

import junit.framework.TestCase.assertEquals
import org.junit.Test

class StringExtensionsTest {

    @Test
    fun `formatDate returns formatted date for valid date format` () {
        val input = "2024-09-21T08:15:30.123Z"
        val result = input.formatDate()

        assertEquals("Sep 21, 2024", result)
    }

    @Test
    fun `formatDate returns original string for invalid date format` () {
        val input = "not-a-date"
        val result = input.formatDate()

        assertEquals("not-a-date", result)
    }

    @Test
    fun `formatDate returns original string for empty string` () {
        val input = ""
        val result = input.formatDate()

        assertEquals("", result)
    }
}