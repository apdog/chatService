package org.example

data class Chat(
    val chatId: Int = 0,
    val userId: Int,
    var photo: String?,
    var messages: MutableList<Message> = mutableListOf(),
    var markChatAsRead: Boolean = false
)
