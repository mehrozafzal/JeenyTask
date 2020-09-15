package com.jeeny.task

import org.junit.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.Charset

class APIAvailabilityTest {
    @Test
    @Throws(Exception::class)
    fun testAvailability() {
        val connection =
            URL("https://pastebin.com/raw/SwFd1znM")
                .openConnection()
        val response = connection.getInputStream()
        val buffer = StringBuffer()
        BufferedReader(
            InputStreamReader(
                response,
                Charset.defaultCharset()
            )
        ).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                buffer.append(line)
            }
        }
        assert(buffer.isNotEmpty())
    }
}