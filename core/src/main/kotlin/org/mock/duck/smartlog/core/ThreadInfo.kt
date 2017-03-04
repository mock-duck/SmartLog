package org.mock.duck.smartlog.core

import java.util.ArrayList

class ThreadInfo {
    private val defaultMethodOffset: Int = 6

    fun currentThreadName(): String {
        return Thread.currentThread().name
    }

    fun currentThreadId(): Long {
        return Thread.currentThread().id
    }

    fun invokers(): List<String> {
        val stacks = Thread.currentThread().stackTrace
        val invokers: List<String> = getInvokers(stacks)

        return invokers
    }

    private fun getInvokers(stacks: Array<StackTraceElement>): List<String> {
        val invokers = ArrayList<String>()
        val startIndex = getStackOffset(stacks)

        if (startIndex == -1) {
            return invokers
        }

        val endIndex = startIndex + defaultMethodOffset

        (startIndex..endIndex - 1).mapTo(invokers) {
            String.format("%s.%s:%s", stacks[it].fileName, stacks[it].methodName, stacks[it].lineNumber)
        }
        return invokers
    }

    private fun getStackOffset(stacks: Array<StackTraceElement>): Int {
        var offsetStart = -1
        var counter = 1

        stacks.forEach {
            if (it.className.contentEquals("org.mock.duck.smartlog.core.ThreadInfo")
                    || it.className.contentEquals("org.mock.duck.smartlog.printer.RawPrinter")
                    || it.className.contentEquals("org.mock.duck.smartlog.core.Data")
                    || it.className.contentEquals("org.mock.duck.smartlog.core.SmartLog")) {

                offsetStart = counter
            }
            counter++
        }
        return offsetStart++
    }
}