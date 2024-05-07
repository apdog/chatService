package org.example

object ChatService {
    private var chatsList: MutableList<Chat> = mutableListOf()
    private var messagesList: MutableList<Message> = mutableListOf()

    private var startChatId = 0
    private var startMessageId = 0

    fun deleteChat(chatId: Int): Int {
        val chatForRemove = getChatById(chatId)
            ?: throw ObjectNotFoundException("Чат с id $chatId не был удален, так как его не существует")
        chatsList.remove(chatForRemove)
        if (chatsList.isEmpty()) print("Пока чатов ни с кем нет")
        return 1
    }

    fun getChats(): List<Chat> {
        return chatsList
    }

    fun getUnreadChatsCount(): Int = chatsList.count { !it.markChatAsRead }

    fun getLastMessages(chatId: Int): List<Message> {
        val chat = getChatById(chatId)
            ?: throw ObjectNotFoundException("Чат с id $chatId не существует")
        return if (chat.messages.isNotEmpty()) {
            chat.messages.takeLast(5)
        } else {
            println("Нет сообщений")
            listOf()
        }
    }

    fun getMessages(userId: Int, countOfMessages: Int): List<Message> {
        val filteredMessagesList = messagesList.filter { it.userId == userId }
        if (filteredMessagesList.isEmpty()) {
            println("Сообщений с данным пользователем нет")
            return listOf()
        }
        filteredMessagesList.forEach { it.markMessageAsRead }
        return filteredMessagesList.takeLast(countOfMessages)
    }

    fun deleteMessage(chatId: Int, messageId: Int): Int {
        val chat = getChatById(chatId)
            ?: throw ObjectNotFoundException("Чат с id $chatId не был удален, так как его не существует")
        val messageToDelete = chat.messages.find { it.messageId == messageId }
            ?: throw ObjectNotFoundException("Сообщение с ID $messageId в чате $chatId не найдено")
        chat.messages.remove(messageToDelete)
        return 1
    }

    fun addMessage(chatId: Int, message: Message, userId: Int): Int {
        messagesList.addMessage(message, chatId, userId)
        return 1
    }

    private fun createChat(userId: Int): Chat {
        startChatId++
        val newChatId = startChatId
        return Chat(
            chatId = newChatId,
            userId = userId,
            photo = null,
            messages = mutableListOf(),
            markChatAsRead = false
        ).also {
            chatsList.add(it)
        }
    }

    private fun createChatCheck(chatId: Int, userId: Int): Chat? {
        return getChatById(chatId) ?: createChat(userId)
    }

    private fun getChatById(chatId: Int): Chat? {
        return chatsList.find { it.chatId == chatId }
    }

    private fun MutableList<Message>.addMessage(message: Message, chatId: Int, userId: Int) {
        message.messageId = ++startMessageId
        this.add(message)
        val chat = createChatCheck(chatId, userId)
        chat?.let { it ->
            it.messages.add(message)
            // Проверяем, все ли сообщения в чате прочитаны
            if (it.messages.all { it.markMessageAsRead }) {
                it.markChatAsRead = true
            } else {
                it.markChatAsRead = false
            }
        }
    }

    fun printChats() {
        val allChats = getChats()
        for (chat in allChats) {
            println(
                "Чат c: ${chat.userId} (ID чата ${chat.chatId})\n" +
                        "${chat.messages.last().message} /Прочитан: ${chat.messages.last().markMessageAsRead}"
            )
        }
    }

    fun clearForTests() {
        chatsList.clear()
        messagesList.clear()
        startChatId = 0
        startMessageId = 0
    }
}
