package org.mock.duck.smartlog.core

import android.content.Context

data class Data(val message: String,
                val tag: String? = null,
                val extras : Set<LogExtras>? = null,
                val context : Context? = null) {

    fun log(priority: Int, config: Config) {
        if (config.logMode == LogMode.PROD) {
            return
        }

        if (tag == null) {
            config.printer.print(priority, this, config.defaultTag)
        } else {
            config.printer.print(priority, this, tag)
        }
    }
}