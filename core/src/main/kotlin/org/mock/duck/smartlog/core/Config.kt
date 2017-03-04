package org.mock.duck.smartlog.core

data class Config(val defaultTag: String, val logMode : LogMode, val printer : Printer, val withStictModeLogging: Boolean)