package org.mock.duck.smartlog.core

import android.os.StrictMode
import android.util.Log

class SmartLog {
    var config: Config? = null

    fun configure(message: String, logMode: LogMode, printer: Printer, isStrictMode: Boolean) {
        config = Config(message, logMode, printer, isStrictMode)

        if (config!!.withStictModeLogging && config!!.logMode == LogMode.STAGE) {
            enableStrictModeLogging()
        }
    }

    fun v(data: Data) {
        data.log(Log.VERBOSE, config!!)
    }

    fun i(data: Data) {
        data.log(Log.INFO, config!!)
    }

    fun d(data: Data) {
        data.log(Log.DEBUG, config!!)
    }

    fun w(data: Data) {
        data.log(Log.WARN, config!!)
    }

    fun e(data: Data) {
        data.log(Log.ERROR, config!!)
    }

    private fun enableStrictModeLogging() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .detectAll()
                .penaltyLog()
                .build())
    }
}