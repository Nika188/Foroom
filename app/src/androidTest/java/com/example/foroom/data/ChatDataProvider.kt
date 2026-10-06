package com.example.foroom.data

import java.util.UUID

object ChatDataProvider {
    fun uniqueChatName(): String {
        return Constants.CHAT_NAME + " " +
                UUID.randomUUID().toString().take(Constants.UNIQUE_CHAT_NAME_SUFFIX_LENGTH)
    }
}
