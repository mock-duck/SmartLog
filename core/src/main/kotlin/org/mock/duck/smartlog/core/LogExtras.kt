package org.mock.duck.smartlog.core


/*class LogExtras {
    var identifier: String? = null
    var value: Any? = null
    var type: Type? = null

    constructor(key: String, json: String) {
        identifier = key
        value = json
        type = Type.JSON
    }

    constructor(key: String, error: Throwable) {
        identifier = key
        value = error
        type = Type.THROWABLE
    }
}*/
data class LogExtras(val key : String, val value : Any, val type : Type? = Type.NONE)

enum class Type {
    JSON, THROWABLE, STRING, INT, BOOL, LONG, DOUBLE, FLOAT, OBJECT, NONE
}