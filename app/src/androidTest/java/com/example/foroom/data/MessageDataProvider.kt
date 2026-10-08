package com.example.foroom.data

import java.util.UUID

object MessageDataProvider {

    fun uniqueMessage(text: String): String {
        return text + " " +
                UUID.randomUUID().toString().take(Constants.UNIQUE_MESSAGE_SUFFIX_LENGTH)
    }

    fun uniqueMessages(text: String, count: Int): List<String> {
        return List(count) { uniqueMessage(text) }
    }
}
