package org.mock.duck.core

import org.junit.Test
import org.mock.duck.smartlog.core.ThreadInfo
import kotlin.test.assertEquals


class ThreadInfoTest {
    @Test
    fun shouldGetMainThreadInfo() {
        val info: ThreadInfo = ThreadInfo()
        assertEquals(info.currentThreadName(), "main", "Default thread should be main thread.")
    }

    @Test
    fun shouldGetMainThreadIdAsDefaultId() {
        val info: ThreadInfo = ThreadInfo()
        assertEquals(info.currentThreadId(), 1, "Default thread id should be 1.")
    }

    @Test
    fun firstInvokerShouldBeThisMethod() {
        val info: ThreadInfo = ThreadInfo()
        assertEquals(info.invokers()[0],"ThreadInfoTest.kt.firstInvokerShouldBeThisMethod:24")
    }
}