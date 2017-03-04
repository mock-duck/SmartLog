package org.mock.duck.smartlog

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.mock.duck.smartlog.core.*
import org.mock.duck.smartlog.core.tools.PrintFormats.*


class RawPrinter : Printer {

    override fun print(priority: Int, logData: Data, tag: String) {
        val threadInfo: ThreadInfo = ThreadInfo()

        val logMessageBuilder: StringBuilder = StringBuilder()
                .append(tread_name_header.value)
                .append(threadInfo.currentThreadName())
                .append(separator.value)
                .append(treadId_header.value)
                .append(threadInfo.currentThreadId())
                .append(separator.value)
                .append(invoker_header.value)
                .append(threadInfo.invokers())
                .append(separator)
                .append(message_header.value)
                .append(logData.message)
                .append(getRawExtras(logData.extras))

        log(priority, tag, logMessageBuilder.toString())
    }

    private fun getRawExtras(extras: Set<LogExtras>?): String {
        val extrasBuilder: StringBuilder = StringBuilder()

        if (extras != null) {
            extrasBuilder.append(separator)
            extrasBuilder.append(extras_header.value)
        }

        extras?.forEach {
            extrasBuilder.append(space.value)
            if (it.type == Type.THROWABLE) {
                extrasBuilder.append(new_line.value)
                extrasBuilder.append(Log.getStackTraceString(it.value as Throwable))
            } else if (it.type == Type.JSON)
                extrasBuilder.append(getRawJsonString(it.value as String))
            else {
                extrasBuilder.append(it.value)
            }
        }
        return extrasBuilder.toString()
    }

    private fun getRawJsonString(json: String): String {
        if (json.startsWith("[") && json.endsWith("]")) {
            try {
                val jsonArray: JSONArray = JSONArray(json)
                return jsonArray.toString()
            } catch (e: JSONException) {
                return Log.getStackTraceString(e)
            }
        } else if (json.startsWith("{") && json.endsWith("}")) {
            try {
                val jsonObject: JSONObject = JSONObject(json)
                return jsonObject.toString()
            } catch (e: JSONException) {
                return Log.getStackTraceString(e)
            }
        } else {
            return ""
        }
    }

    private fun log(priority: Int, tag: String, message: String) {
        synchronized(this) {
            Log.println(priority, tag, message)
        }
    }
}