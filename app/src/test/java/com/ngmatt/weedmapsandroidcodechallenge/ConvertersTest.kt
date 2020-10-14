package com.ngmatt.weedmapsandroidcodechallenge

import com.ngmatt.weedmapsandroidcodechallenge.storage.ModelTypeConverters
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.*

class ConvertersTest {

    private val testDate = Date().apply {
        time = 12345678
    }

    @Test
    fun `test date to timestamp`() {
        assertEquals(testDate.time, ModelTypeConverters().dateToTimeStamp(testDate))
    }

    @Test
    fun `test timestamp to date`() {
        assertEquals(testDate, ModelTypeConverters().fromMillis(12345678))
    }
}