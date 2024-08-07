import org.example.ChatService
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class ChatServiceTest {

    @Before
    fun clearBeforeTest() {
        ChatService.clear()
    }

    @Test (expected = Exception::class)
    fun deleteMessageFail() {

        val service = ChatService
        ChatService.createMessage(1, 1, "text of message 1 contact 1")
        service.deleteMessage(2, 1)

    }

    @Test
    fun deleteMessageSuccess() {

        val service = ChatService
        ChatService.createMessage(1, 1, "text of message 1 contact 1")
        service.deleteMessage(1, 1)

    }
}