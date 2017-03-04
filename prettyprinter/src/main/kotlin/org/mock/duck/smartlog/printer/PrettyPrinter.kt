package org.mock.duck.smartlog.printer

import android.util.Log
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.mock.duck.smartlog.core.*


class PrettyPrinter : Printer {
    private val defaultShift: String = "   "

    override fun print(priority: Int, logData: Data, tag: String) {
        val threadInfo: ThreadInfo = ThreadInfo()

        logThreadInfo(priority, threadInfo, tag)
        logInvokers(priority, threadInfo, tag)
        logMessage(priority, logData, tag)
        logExtras(priority, logData, tag)
    }

    private fun logThreadInfo(priority: Int, threadInfo: ThreadInfo, tag: String) {
        log(priority, tag, "Thread: " + threadInfo.currentThreadName())
        log(priority, tag, "Thread-Id: " + threadInfo.currentThreadId())
    }

    private fun logInvokers(priority: Int, threadInfo: ThreadInfo, tag: String) {
        log(priority, tag, "Invokers: ")
        threadInfo.invokers().forEach { log(priority, tag, defaultShift + it) }
    }

    private fun logMessage(priority: Int, logData: Data, tag: String) {
        log(priority, tag, "Message: " + logData.message)
    }

    private fun logExtras(priority: Int, logData: Data, tag: String) {
        log(priority, tag, "Extras: ")

        logData.extras?.forEach {
            if (it.type == Type.THROWABLE) {
                log(priority, tag, Log.getStackTraceString(it.value as Throwable))
            } else {
                logJsonPretty(priority, it, tag)
            }
        }
    }

    private fun logJsonPretty(priority: Int, extra: LogExtras, tag: String) {
        if (extra.type == Type.JSON) {
            val json: String = extra.value as String
            if (json.startsWith("{") && json.endsWith("}")) {
                try {
                    val jsonAsObject: JSONObject = JSONObject(json)
                    log(priority, tag, jsonAsObject.toString(1))
                } catch (e: JSONException) {
                    log(priority, tag, Log.getStackTraceString(e))
                }
            } else if (json.startsWith("[") && json.endsWith("]")) {
                try {
                    val jsonArray: JSONArray = JSONArray(json)
                    log(priority, tag, jsonArray.toString(1))
                } catch (e: JSONException) {
                    log(priority, tag, Log.getStackTraceString(e))
                }
            }
        } else {
            try {
                val jsonAsObject: JSONObject = JSONObject(Gson().toJson(extra))
                log(priority, tag, jsonAsObject.toString(1))
            } catch (e: JSONException) {
                log(priority, tag, Log.getStackTraceString(e))
            }
        }
    }

    private fun log(priority: Int, tag: String, message: String) {
        synchronized(this) {
            Log.println(priority, tag, message)
        }
    }
}