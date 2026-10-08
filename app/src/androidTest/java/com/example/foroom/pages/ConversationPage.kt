package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR

class ConversationPage {
    val messageInput: Matcher<View> = withId(R.id.messageInput)
    val messagesRecyclerView: Matcher<View> = withId(R.id.messagesRecyclerView)

    val chatHeader: Matcher<View> = allOf(withId(R.id.chatHeaderView), hasSibling(messageInput))
    val closeButton: Matcher<View> = allOf(withId(R.id.closeButton), hasSibling(messageInput))
    val chatName: Matcher<View> =
        allOf(withId(DesignR.id.chatNameTextView), isDescendantOfA(chatHeader))

    val messageEditText: Matcher<View> =
        allOf(withId(DesignR.id.inputEditText), isDescendantOfA(messageInput))

    val sendMessageButton: Matcher<View> =
        allOf(withId(R.id.sendMessageButton), isDescendantOfA(messageInput))

    fun message(text: String): Matcher<View> = allOf(
        withId(R.id.messageView),
        isDescendantOfA(messagesRecyclerView),
        hasDescendant(allOf(withId(DesignR.id.messageTextView), withText(text)))
    )

    fun messageSender(text: String): Matcher<View> =
        allOf(withId(DesignR.id.userNameTextView), isDescendantOfA(message(text)))
}
