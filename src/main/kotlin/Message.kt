package org.example

import java.util.*

data class Message(
    var messageId: Int = 0,
    var userId: Int = 0,
    var message: String,
    var attachments: MutableList<Attachment>,
    val date: Date,
    var markMessageAsRead: Boolean = false
)
