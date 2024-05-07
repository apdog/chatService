import org.example.Chat
import org.example.ChatService.addMessage
import org.example.ChatService.clearForTests
import org.example.ChatService.deleteChat
import org.example.ChatService.deleteMessage
import org.example.ChatService.getChats
import org.example.ChatService.getLastMessages
import org.example.ChatService.getMessages
import org.example.ChatService.getUnreadChatsCount
import org.example.ChatService.printChats
import org.example.Message
import org.example.ObjectNotFoundException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.*

class ChatServiceTest {

    @Test
    fun deleteChatTest() {
        val message = Message(
            messageId = 0,
            userId = 777,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        addMessage(1, message, 777)
        val index = deleteChat(1)
        assertEquals(1, index)
    }

    @Test(expected = ObjectNotFoundException::class)
    fun deleteChatExceptionForNonExistingChat() {
        val message = Message(
            messageId = 0,
            userId = 777,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        addMessage(1, message, 777)
        deleteChat(2)
    }

    @Test
    fun deleteChatEmpty() {
        val message = Message(
            messageId = 0,
            userId = 777,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        addMessage(1, message, 777)
        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
        deleteChat(1)
        val output = outContent.toString().trim()
        assertEquals("Пока чатов ни с кем нет", output)
    }

    @Test
    fun getChatsTest() {
        val message = Message(
            messageId = 0,
            userId = 777,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        val chat =
            Chat(chatId = 1, userId = 777, photo = null, messages = mutableListOf(message), markChatAsRead = false)
        addMessage(1, message, 777)
        val check = getChats()
        assertEquals(listOf(chat), check)
    }

    @Test
    fun getUnreadChatsCountTest() {
        val message = Message(
            messageId = 0,
            userId = 777,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        val message2 = Message(
            messageId = 0,
            userId = 555,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = true
        )
        addMessage(0, message, 777)
        addMessage(1, message2, 555)
        val index = getUnreadChatsCount()
        assertEquals(1, index)
    }

    @Test(expected = ObjectNotFoundException::class)
    fun getLastMessagesExceptionForNonExistingChat() {
        val message = Message(
            messageId = 0,
            userId = 777,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        addMessage(1, message, 777)
        getLastMessages(2)
    }

    @Test
    fun getLastMessagesTest() {
        val message = Message(
            messageId = 1,
            userId = 777,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        val message2 = Message(
            messageId = 2,
            userId = 777,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        addMessage(0, message, 777)
        addMessage(1, message2, 777)
        val index = getLastMessages(1)
        val checkList = listOf(message, message2)
        assertEquals(checkList, index)
    }

    @Test
    fun getLastMessageEmpty() {
        val message = Message(
            messageId = 0,
            userId = 777,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        addMessage(0, message, 777)
        deleteMessage(1, 1)
        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
        getLastMessages(1)
        val output = outContent.toString().trim()
        assertEquals("Нет сообщений", output)
    }

    @Test
    fun getMessagesTest() {
        val message = Message(
            messageId = 1,
            userId = 777,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        val message2 = Message(
            messageId = 2,
            userId = 777,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        addMessage(0, message, 777)
        addMessage(1, message2, 777)
        val index = getMessages(777, 5)
        val checkList = listOf(message, message2)
        assertEquals(checkList, index)
    }

    @Test
    fun getMessagesNonExistTest() {
        val message = Message(
            messageId = 1,
            userId = 555,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        val message2 = Message(
            messageId = 2,
            userId = 333,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        addMessage(0, message, 555)
        addMessage(1, message2, 333)
        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
        getMessages(777, 5)
        val output = outContent.toString().trim()
        assertEquals("Сообщений с данным пользователем нет", output)
    }

    @Test
    fun deleteMessageTest() {
        val message = Message(
            messageId = 0,
            userId = 777,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        addMessage(1, message, 777)
        val index = deleteMessage(1, 1)
        assertEquals(1, index)
    }

    @Test(expected = ObjectNotFoundException::class)
    fun deleteMessageExceptionForNonExistingMessage() {
        val message = Message(
            messageId = 0,
            userId = 777,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        addMessage(1, message, 777)
        deleteMessage(1, 2)
    }

    @Test(expected = ObjectNotFoundException::class)
    fun deleteMessageExceptionForNonExistingChat() {
        val message = Message(
            messageId = 0,
            userId = 777,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        addMessage(1, message, 777)
        deleteMessage(2, 1)
    }

    @Test
    fun printChatsTest() {
        val message = Message(
            messageId = 1,
            userId = 555,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        val message2 = Message(
            messageId = 2,
            userId = 333,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        addMessage(0, message, 555)
        addMessage(1, message2, 333)
        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
        printChats()
        val output = outContent.toString().trim()
        assertEquals(
            "Чат c: 555 (ID чата 1)\n" + "Test /Прочитан: false", output
        )
    }

    @Test
    fun addMessageTest() {
        val message = Message(
            messageId = 0,
            userId = 777,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = false
        )
        val index = addMessage(1, message, 777)
        assertEquals(1, index)
    }

    @Test
    fun addMessageTrueTest() {
        val message = Message(
            messageId = 0,
            userId = 777,
            message = "Test",
            attachments = mutableListOf(),
            date = Date(),
            markMessageAsRead = true
        )
        val index = addMessage(1, message, 777)
        assertTrue(message.markMessageAsRead)
    }

    @Before
    fun setUp() {
        clearForTests()
    }
}