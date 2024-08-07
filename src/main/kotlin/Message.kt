package org.example

data class Message(val contactId: Int, val messageId: Int, val text: String, var readed: Boolean = false) {
}