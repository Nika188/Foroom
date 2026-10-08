package com.example.foroom.data

object Constants {
    const val TIMEOUT_SEC = 10L
    const val RETRY_INTERVAL_MS = 50L

    // Demo account of the training build (see TRAINING.md). For a server-connected build
    // replace it with a user created beforehand
    const val EXISTING_USER_NAME = "student"
    const val INCORRECT_PASSWORD = "Wrong_password1"
    const val VALID_PASSWORD = "Passw0rd!23"
    const val NEW_PASSWORD = "NewPassw0rd!45"
    const val AVATAR_INDEX = 1
    const val AVATAR_ID = 1

    const val CHANGE_LANGUAGE_LABEL_KA = "ენის შეცვლა"
    const val CHANGE_LANGUAGE_LABEL_EN = "Change Language"

    const val CHAT_NAME = "Nikoloz Makharadze"
    const val CHAT_IMAGE_INDEX = 2
    const val UNIQUE_CHAT_NAME_SUFFIX_LENGTH = 8

    const val UNIQUE_USER_NAME_PREFIX = "qa_"
    const val UNIQUE_USER_NAME_SUFFIX_LENGTH = 8

    const val USER_A_NAME = "user_a"
    const val USER_B_NAME = "user_b"
    const val USER_B_AVATAR_ID = 2

    const val JOHN_WEEK_CHAT_NAME = "johnWeek"
    const val OWN_CHAT_NAME = "$CHAT_NAME chat"
    const val SHARED_CHAT_NAME = "something"
    const val CHAT_IMAGE_ID = 3

    const val DRINK_MESSAGE = "let's go for a drink"
    const val MODULE_QUESTION = "which module do u like most in automation academy?"
    const val GREETING_MESSAGE = "what's up"
    const val FOLLOW_UP_MESSAGE = "u there?"
    const val REPLY_MESSAGE = "yeah whats up"
    const val FOLLOW_UP_MESSAGES_COUNT = 25
    const val UNIQUE_MESSAGE_SUFFIX_LENGTH = 8
    const val NEWEST_MESSAGE_POSITION = 0
    const val MAX_HISTORY_SWIPES = 60
    const val HISTORY_SWIPE_START_RATIO = 0.3
    const val HISTORY_SWIPE_END_RATIO = 0.7
    const val HISTORY_SWIPE_DURATION_MS = 800
}
