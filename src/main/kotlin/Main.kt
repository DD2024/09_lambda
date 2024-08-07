package org.example

import java.text.Normalizer.Form

fun main() {

   ChatService.createMessage(1, 1, "text of message 1 contact 1")
    ChatService.createMessage(1, 2, "text of message 2 contact 1")
    ChatService.createMessage(2, 1, "text of message 1 contact 2")
    ChatService.createMessage(2, 2, "text of message 2 contact 2")

    val unreadChatsCount = ChatService.getUnreadChatsCount()
    println(unreadChatsCount)

    val chatList = ChatService.getChats()

    val listOfLastMessages = ChatService.getLastMessages()

    val chatMessages = ChatService.getChatMessages(1, 2)

}

object ChatService{

    private var messages = mutableListOf<Message>()

    private var chats = mutableListOf<Chat>()

    fun clear() {
        messages = mutableListOf()
        chats = mutableListOf()
    }

    fun createMessage(contactId: Int, messageId: Int, text: String){

        val message = Message(contactId, messageId, text)
        messages += message

        if (getChat(contactId) == null) {
            val chat = Chat(contactId)
            chats += chat
        }
    }

    fun deleteMessage(contactId: Int, messageId: Int): Int{

        val message = messages.getMessage(contactId, messageId)?:
        throw Exception("no message with contactId: $contactId and messageId: $messageId")

        messages.remove(message)
        return 1

    }

    fun getChat(contactId: Int): Chat?{
        return chats.lastOrNull{it.contactId == contactId}
    }

    fun getChats() = chats

    fun MutableList<Message>.getMessage(contactId: Int, messageId: Int): Message?{

        val messagesByContactId = messages.filter{it.contactId == contactId}
        return  messagesByContactId.lastOrNull{it.messageId == contactId}
    }

    fun deleteChat(contactId: Int){

        val chat = getChat(contactId)?: throw Exception("no chat with contactId: $contactId")

        chats.remove(chat)

        try {
            messages.removeAll(messages.filter { it.contactId == contactId })
        }
        finally {
        }
    }

    fun getUnreadChatsCount(): Int{

        val unreadChatsCount = messages.filter {it.readed == false}.groupBy{it.contactId}.size
        return unreadChatsCount
    }

    fun getLastMessages(): MutableList<Message>{

        val mapChatIdMessages = messages.groupBy {it.contactId}

        val lastMessages = mutableListOf<Message>()

        for(item in mapChatIdMessages) {

                val lastMessage = item.value.maxByOrNull { it.messageId }

            if (lastMessage != null){

                lastMessages += lastMessage
            }else {
                println("no message with contactId: ${item.key}")
            }

        }

        return lastMessages
    }

    fun getChatMessages(contactId: Int, amountOfMessages: Int): List<Message>{

        val chatMessages = messages.filter { it.contactId == contactId }

         val chatMessagesWithamount = chatMessages.takeLast(amountOfMessages)

        for (item in chatMessagesWithamount){

            item.readed = true
        }

        return chatMessagesWithamount
    }

}

