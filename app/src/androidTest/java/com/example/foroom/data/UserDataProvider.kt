package com.example.foroom.data

import java.util.UUID

object UserDataProvider {

    // Never registered before, so it works both as a not existing user and as a new user
    fun uniqueUserName(): String {
        return Constants.UNIQUE_USER_NAME_PREFIX +
                UUID.randomUUID().toString().take(Constants.UNIQUE_USER_NAME_SUFFIX_LENGTH)
    }
}
