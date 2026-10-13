package com.example.foroom.steps

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.design_system.components.image_chooser.ImageChooserListView
import com.example.foroom.Helper.input
import com.example.foroom.Helper.tap
import com.example.foroom.Helper.tapUntilGone
import com.example.foroom.Helper.waitUntilMatches
import com.example.foroom.data.Constants
import com.example.foroom.domain.model.request.LogInRequest
import com.example.foroom.domain.usecase.CreateChatUseCase
import com.example.foroom.domain.usecase.GetChatsUseCase
import com.example.foroom.domain.usecase.LogInUserUseCase
import com.example.foroom.domain.usecase.RemoteSignOutUseCase
import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.CreateChatPage
import com.example.shared.model.Image
import com.example.shared.util.runtime.user_token.UserTokenRuntimeHolder
import kotlinx.coroutines.runBlocking
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.koin.core.context.GlobalContext

class ChatSteps {
    private val createChatPage = CreateChatPage()
    private val chatsPage = ChatsPage()

    fun checkCreateChatScreenIsDisplayed() = apply {
        with(createChatPage) {
            listOf(chatNameInput, chatImageChooser, createChatButton).forEach { element ->
                onView(element).waitUntilMatches(isDisplayed(), Constants.TIMEOUT_SEC)
            }
        }
    }

    fun enterChatName(chatName: String) = apply {
        onView(createChatPage.chatNameEditText).input(chatName)
    }

    fun waitForChatImages() = apply {
        onView(createChatPage.chatImageChooser)
            .waitUntilMatches(chatImageChooserState("chat images are loaded") { list ->
                list.images.isNotEmpty() && list.images.none { it.id == Image.BLANK_IMAGE_ID }
            }, Constants.TIMEOUT_SEC)
    }

    fun selectChatImage(index: Int) = apply {
        onView(createChatPage.chatImageAt(index)).tap()
    }

    fun checkChatImageIsSelected(index: Int) = apply {
        onView(createChatPage.chatImageChooser)
            .waitUntilMatches(chatImageChooserState("selected chat image index is $index") { list ->
                list.selectedIndex == index
            }, Constants.TIMEOUT_SEC)
    }

    fun submitChatCreation() = apply {
        onView(createChatPage.createChatButton).tap()
    }

    fun checkCreatedChatIsDisplayed(chatName: String) = apply {
        onView(createChatPage.createdChatName)
            .waitUntilMatches(allOf(isDisplayed(), withText(chatName)), Constants.TIMEOUT_SEC)
    }

    fun closeCreatedChat() = apply {
        onView(createChatPage.createdChatCloseButton).tap()
    }

    fun searchChat(chatName: String) = apply {
        onView(chatsPage.searchChatEditText).input(chatName)
    }

    fun checkChatIsListed(chatName: String) = apply {
        onView(chatsPage.chatCardTitle(chatName))
            .waitUntilMatches(isDisplayed(), Constants.TIMEOUT_SEC)
    }

    fun openChat(chatName: String) = apply {
        onView(chatsPage.chatCardOpenButton(chatName))
            .tapUntilGone(chatsPage.searchChatInput, Constants.TIMEOUT_SEC)
    }

    fun ensureChatExists(chatName: String, chatImageId: Int, userName: String, password: String) =
        apply {
            runBlocking {
                val koin = GlobalContext.get()
                val tokens = koin.get<UserTokenRuntimeHolder>()

                tokens.setUserToken(
                    koin.get<LogInUserUseCase>()(LogInRequest(userName, password)).token
                )
                try {
                    if (!chatExists(chatName)) koin.get<CreateChatUseCase>()(chatName, chatImageId)
                } finally {
                    koin.get<RemoteSignOutUseCase>()()
                    tokens.setUserToken("")
                }
            }
        }

    private suspend fun chatExists(chatName: String): Boolean {
        val getChats = GlobalContext.get().get<GetChatsUseCase>()
        var page = 0

        while (true) {
            val response = getChats(page++, name = chatName)

            if (response.chats.any { it.name == chatName }) return true
            if (!response.hasNext) return false
        }
    }

    private fun chatImageChooserState(
        stateDescription: String,
        state: (ImageChooserListView) -> Boolean
    ): Matcher<View> =
        object : BoundedMatcher<View, ImageChooserListView>(ImageChooserListView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText(stateDescription)
            }

            override fun matchesSafely(item: ImageChooserListView): Boolean = state(item)
        }
}
