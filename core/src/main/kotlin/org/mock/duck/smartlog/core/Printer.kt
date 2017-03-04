package org.mock.duck.smartlog.core

interface Printer {
    fun print(priority : Int, logData : Data, tag : String)
}