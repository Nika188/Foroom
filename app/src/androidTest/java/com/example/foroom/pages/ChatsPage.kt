package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR

class ChatsPage {
    val searchChatInput: Matcher<View> = withId(R.id.searchChatInput)
    val chatsRecyclerView: Matcher<View> = withId(R.id.chatsRecyclerView)

    val searchChatEditText: Matcher<View> =
        allOf(withId(DesignR.id.inputEditText), isDescendantOfA(searchChatInput))

    fun chatCardTitle(chatName: String): Matcher<View> = allOf(
        withId(DesignR.id.chatTitleTextView),
        withText(chatName),
        isDescendantOfA(chatsRecyclerView)
    )
}
