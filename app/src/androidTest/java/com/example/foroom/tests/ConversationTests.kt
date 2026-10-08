package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.data.Constants
import com.example.foroom.data.MessageDataProvider
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.steps.ChatSteps
import com.example.foroom.steps.ConversationSteps
import com.example.foroom.steps.HomeSteps
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.ProfileSteps
import com.example.foroom.steps.RegistrationSteps
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExternalResource
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConversationTests {
    private val loginSteps = LoginSteps()
    private val registrationSteps = RegistrationSteps()
    private val homeSteps = HomeSteps()
    private val profileSteps = ProfileSteps()
    private val chatSteps = ChatSteps()
    private val conversationSteps = ConversationSteps()

    @get:Rule(order = 0)
    val preparedConversationsRule = object : ExternalResource() {
        override fun before() {
            loginSteps.clearSavedSession()

            registrationSteps
                .ensureAccountExists(
                    Constants.USER_A_NAME, Constants.VALID_PASSWORD, Constants.AVATAR_ID
                )
                .ensureAccountExists(
                    Constants.USER_B_NAME, Constants.VALID_PASSWORD, Constants.USER_B_AVATAR_ID
                )

            listOf(
                Constants.JOHN_WEEK_CHAT_NAME, Constants.OWN_CHAT_NAME, Constants.SHARED_CHAT_NAME
            ).forEach { chatName ->
                chatSteps.ensureChatExists(
                    chatName,
                    Constants.CHAT_IMAGE_ID,
                    Constants.USER_A_NAME,
                    Constants.VALID_PASSWORD
                )
            }
        }
    }

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    @Before
    fun setUp() {
        loginSteps
            .checkLoginScreenIsDisplayed()
            .logIn(Constants.USER_A_NAME, Constants.VALID_PASSWORD)

        homeSteps.checkHomeScreenIsDisplayed()
    }

    @Test
    fun sentMessageRemainsInJohnWeekChatAfterReopening() {
        val message = MessageDataProvider.uniqueMessage(Constants.DRINK_MESSAGE)

        chatSteps
            .searchChat(Constants.JOHN_WEEK_CHAT_NAME)
            .checkChatIsListed(Constants.JOHN_WEEK_CHAT_NAME)
            .openChat(Constants.JOHN_WEEK_CHAT_NAME)

        conversationSteps
            .checkConversationIsOpen(Constants.JOHN_WEEK_CHAT_NAME)
            .enterMessage(message)
            .submitMessage()
            .checkNewMessageIsDisplayed(message)
            .closeConversation()

        homeSteps.checkHomeScreenIsDisplayed()

        chatSteps
            .searchChat(Constants.JOHN_WEEK_CHAT_NAME)
            .checkChatIsListed(Constants.JOHN_WEEK_CHAT_NAME)
            .openChat(Constants.JOHN_WEEK_CHAT_NAME)

        conversationSteps
            .checkConversationIsOpen(Constants.JOHN_WEEK_CHAT_NAME)
            .checkMessageIsDisplayed(message)
    }

    @Test
    fun sentQuestionIsDisplayedInOwnChat() {
        val question = MessageDataProvider.uniqueMessage(Constants.MODULE_QUESTION)

        chatSteps
            .searchChat(Constants.OWN_CHAT_NAME)
            .checkChatIsListed(Constants.OWN_CHAT_NAME)
            .openChat(Constants.OWN_CHAT_NAME)

        conversationSteps
            .checkConversationIsOpen(Constants.OWN_CHAT_NAME)
            .enterMessage(question)
            .submitMessage()
            .checkNewMessageIsDisplayed(question)
    }

    @Test
    fun conversationIsContinuedByAnotherAccount() {
        val greeting = MessageDataProvider.uniqueMessage(Constants.GREETING_MESSAGE)
        val reply = MessageDataProvider.uniqueMessage(Constants.REPLY_MESSAGE)

        chatSteps
            .searchChat(Constants.SHARED_CHAT_NAME)
            .checkChatIsListed(Constants.SHARED_CHAT_NAME)
            .openChat(Constants.SHARED_CHAT_NAME)

        conversationSteps
            .checkConversationIsOpen(Constants.SHARED_CHAT_NAME)
            .enterMessage(greeting)
            .submitMessage()
            .checkNewMessageIsDisplayed(greeting)
            .sendMessages(
                MessageDataProvider.uniqueMessages(
                    Constants.FOLLOW_UP_MESSAGE, Constants.FOLLOW_UP_MESSAGES_COUNT
                )
            )
            .closeConversation()

        homeSteps
            .checkHomeScreenIsDisplayed()
            .openProfile()

        profileSteps
            .checkProfileScreenIsDisplayed()
            .signOut()

        loginSteps
            .checkLoginScreenIsDisplayed()
            .logIn(Constants.USER_B_NAME, Constants.VALID_PASSWORD)

        homeSteps.checkHomeScreenIsDisplayed()

        chatSteps
            .searchChat(Constants.SHARED_CHAT_NAME)
            .checkChatIsListed(Constants.SHARED_CHAT_NAME)
            .openChat(Constants.SHARED_CHAT_NAME)

        conversationSteps
            .checkConversationIsOpen(Constants.SHARED_CHAT_NAME)
            .scrollToOlderMessage(greeting)
            .checkMessageIsDisplayed(greeting)
            .checkMessageSender(greeting, Constants.USER_A_NAME)
            .enterMessage(reply)
            .submitMessage()
            .checkNewMessageIsDisplayed(reply)
            .closeConversation()

        homeSteps
            .checkHomeScreenIsDisplayed()
            .openProfile()

        profileSteps
            .checkProfileScreenIsDisplayed()
            .signOut()

        loginSteps
            .checkLoginScreenIsDisplayed()
            .logIn(Constants.USER_A_NAME, Constants.VALID_PASSWORD)

        homeSteps.checkHomeScreenIsDisplayed()

        chatSteps
            .searchChat(Constants.SHARED_CHAT_NAME)
            .checkChatIsListed(Constants.SHARED_CHAT_NAME)
            .openChat(Constants.SHARED_CHAT_NAME)

        conversationSteps
            .checkConversationIsOpen(Constants.SHARED_CHAT_NAME)
            .checkMessageIsDisplayed(reply)
            .checkMessageSender(reply, Constants.USER_B_NAME)
    }
}
