package com.example.foroom.steps

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.foroom.Helper.input
import com.example.foroom.Helper.swiper
import com.example.foroom.Helper.tap
import com.example.foroom.Helper.waitUntilMatches
import com.example.foroom.data.Constants
import com.example.foroom.pages.ConversationPage
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ConversationSteps {
    private val conversationPage = ConversationPage()

    fun checkConversationIsOpen(chatName: String) = apply {
        with(conversationPage) {
            onView(this.chatName)
                .waitUntilMatches(allOf(isDisplayed(), withText(chatName)), Constants.TIMEOUT_SEC)
            onView(messagesRecyclerView).waitUntilMatches(isDisplayed(), Constants.TIMEOUT_SEC)
            onView(sendMessageButton)
                .waitUntilMatches(allOf(isDisplayed(), isEnabled()), Constants.TIMEOUT_SEC)
        }
    }

    fun enterMessage(text: String) = apply {
        onView(conversationPage.messageEditText).input(text)
    }

    fun submitMessage() = apply {
        onView(conversationPage.sendMessageButton)
            .waitUntilMatches(allOf(isDisplayed(), isEnabled()), Constants.TIMEOUT_SEC)
            .tap()
    }

    fun sendMessages(messages: List<String>) = apply {
        messages.forEach { message ->
            enterMessage(message)
            submitMessage()
            checkNewMessageIsDisplayed(message)
        }
    }

    fun checkMessageIsDisplayed(text: String) = apply {
        onView(conversationPage.message(text))
            .waitUntilMatches(isDisplayed(), Constants.TIMEOUT_SEC)
    }

    fun checkNewMessageIsDisplayed(text: String) = apply {
        val endTime = System.currentTimeMillis() + Constants.TIMEOUT_SEC * 1000

        while (true) {
            try {
                onView(conversationPage.messagesRecyclerView).perform(
                    scrollToPosition<RecyclerView.ViewHolder>(Constants.NEWEST_MESSAGE_POSITION)
                )
                onView(conversationPage.message(text)).check(matches(isDisplayed()))
                return@apply
            } catch (error: Throwable) {
                if (System.currentTimeMillis() >= endTime) throw error
                Thread.sleep(Constants.RETRY_INTERVAL_MS)
            }
        }
    }

    fun checkMessageSender(text: String, senderName: String) = apply {
        onView(conversationPage.messageSender(text))
            .waitUntilMatches(allOf(isDisplayed(), withText(senderName)), Constants.TIMEOUT_SEC)
    }

    fun scrollToOlderMessage(text: String) = apply {
        repeat(Constants.MAX_HISTORY_SWIPES) {
            if (isMessageInMessageArea(text)) return@apply
            swipeToOlderMessages()
        }
    }

    fun closeConversation() = apply {
        onView(conversationPage.closeButton).tap()
    }

    private fun swipeToOlderMessages() {
        val messageArea = messageArea()

        swiper(
            (messageArea.top + messageArea.height() * Constants.HISTORY_SWIPE_START_RATIO).toInt(),
            (messageArea.top + messageArea.height() * Constants.HISTORY_SWIPE_END_RATIO).toInt(),
            Constants.HISTORY_SWIPE_DURATION_MS
        )
    }

    private fun isMessageInMessageArea(text: String): Boolean {
        return try {
            val message = screenBounds(conversationPage.message(text))
            val messageArea = messageArea()

            message.top >= messageArea.top && message.bottom <= messageArea.bottom
        } catch (_: NoMatchingViewException) {
            false
        }
    }

    private fun messageArea(): Rect {
        val header = screenBounds(conversationPage.chatHeader)
        val input = screenBounds(conversationPage.messageInput)

        return Rect(header.left, header.bottom, header.right, input.top)
    }

    private fun screenBounds(element: Matcher<View>): Rect {
        val bounds = Rect()
        onView(element).check { view, noViewFoundException ->
            if (noViewFoundException != null) throw noViewFoundException

            val location = IntArray(2)
            view.getLocationOnScreen(location)
            bounds.set(
                location[0], location[1], location[0] + view.width, location[1] + view.height
            )
        }
        return bounds
    }
}
