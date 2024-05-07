package org.example

import java.util.*

fun main() {
    val message = Message(
        message = "Это первое сообщение тебе", attachments = mutableListOf(), date = Date()
    )
    val message2 = Message(
        message = "ты опоздал на пару!", attachments = mutableListOf(), date = Date(), markMessageAsRead = true
    )
    with(ChatService){
        addMessage(
            chatId = 0, message = message, userId = 777)
        addMessage(
            chatId = 1, message = message2, userId = 777)
//        deleteChat(1)
        printChats()
    }
}