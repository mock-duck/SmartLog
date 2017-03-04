package org.mock.duck.smartlog.core.tools

enum class PrintFormats(val value: String) {
    separator(" - "),
    space(" "),
    new_line("\n"),
    tread_name_header("Thread: "),
    treadId_header("Thread-Id: "),
    invoker_header("Invoker: "),
    message_header("Message: "),
    extras_header("Extras: ")
}