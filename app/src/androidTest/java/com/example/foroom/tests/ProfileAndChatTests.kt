package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.data.ChatDataProvider
import com.example.foroom.data.Constants
import com.example.foroom.data.UserDataProvider
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.steps.ChatSteps
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
class ProfileAndChatTests {
    private val loginSteps = LoginSteps()
    private val registrationSteps = RegistrationSteps()
    private val homeSteps = HomeSteps()
    private val profileSteps = ProfileSteps()
    private val chatSteps = ChatSteps()
    private val userName = UserDataProvider.uniqueUserName()

    @get:Rule(order = 0)
    val dedicatedAccountRule = object : ExternalResource() {
        override fun before() {
            loginSteps.clearSavedSession()
            registrationSteps.createAccount(
                userName, Constants.VALID_PASSWORD, Constants.AVATAR_ID
            )
        }
    }

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    @Before
    fun setUp() {
        loginSteps
            .checkLoginScreenIsDisplayed()
            .enterUserName(userName)
            .enterPassword(Constants.VALID_PASSWORD)
            .submitLogin()

        homeSteps.checkHomeScreenIsDisplayed()
    }

    @Test
    fun changedPasswordIsAcceptedOnNextLogin() {
        homeSteps.openProfile()

        profileSteps
            .checkProfileScreenIsDisplayed()
            .openChangePassword()
            .enterNewPassword(Constants.NEW_PASSWORD)
            .enterRepeatNewPassword(Constants.NEW_PASSWORD)
            .confirmPasswordChange()

        loginSteps
            .checkLoginScreenIsDisplayed()
            .enterUserName(userName)
            .enterPassword(Constants.NEW_PASSWORD)
            .submitLogin()

        homeSteps.checkHomeScreenIsDisplayed()
    }

    @Test
    fun profileLanguageSwitchesBetweenGeorgianAndEnglish() {
        homeSteps.openProfile()

        profileSteps
            .checkProfileScreenIsDisplayed()
            .openChangeLanguage()
            .selectGeorgianLanguage()
            .checkChangeLanguageLabel(Constants.CHANGE_LANGUAGE_LABEL_KA)
            .openChangeLanguage()
            .selectEnglishLanguage()
            .checkChangeLanguageLabel(Constants.CHANGE_LANGUAGE_LABEL_EN)
            .openChangeLanguage()
            .selectGeorgianLanguage()
            .checkChangeLanguageLabel(Constants.CHANGE_LANGUAGE_LABEL_KA)
    }

    @Test
    fun createdChatIsFoundInChatList() {
        val chatName = ChatDataProvider.uniqueChatName()

        homeSteps.openCreateChat()

        chatSteps
            .checkCreateChatScreenIsDisplayed()
            .enterChatName(chatName)
            .waitForChatImages()
            .selectChatImage(Constants.CHAT_IMAGE_INDEX)
            .checkChatImageIsSelected(Constants.CHAT_IMAGE_INDEX)
            .submitChatCreation()
            .checkCreatedChatIsDisplayed(chatName)
            .closeCreatedChat()

        homeSteps.checkHomeScreenIsDisplayed()

        chatSteps
            .searchChat(chatName)
            .checkChatIsListed(chatName)
    }
}
