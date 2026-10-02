package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.data.Constants
import com.example.foroom.data.UserDataProvider
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.steps.HomeSteps
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.RegistrationSteps
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExternalResource
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginAndRegistrationTests {
    private val loginSteps = LoginSteps()
    private val registrationSteps = RegistrationSteps()
    private val homeSteps = HomeSteps()

    // Has to run before the activity is launched, so every test starts from the login screen
    // regardless of the test execution order
    @get:Rule(order = 0)
    val signedOutRule = object : ExternalResource() {
        override fun before() {
            loginSteps.clearSavedSession()
        }
    }

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    @Before
    fun setUp() {
        loginSteps.checkLoginScreenIsDisplayed()
    }

    @Test
    fun validUserNameAndInvalidPassword() {
        loginSteps
            .enterUserName(Constants.EXISTING_USER_NAME)
            .enterPassword(Constants.INCORRECT_PASSWORD)
            .submitLogin()
            .checkPasswordErrorIsDisplayed()
    }

    @Test
    fun invalidUserNameAndInvalidPassword() {
        loginSteps
            .enterUserName(UserDataProvider.uniqueUserName())
            .enterPassword(Constants.INCORRECT_PASSWORD)
            .submitLogin()
            .checkUserNameErrorIsDisplayed()
            .checkPasswordErrorIsDisplayed()
    }

    @Test
    fun successfulRegistration() {
        loginSteps.openRegistration()

        registrationSteps
            .checkRegistrationScreenIsDisplayed()
            .enterUserName(UserDataProvider.uniqueUserName())
            .enterPassword(Constants.VALID_PASSWORD)
            .enterRepeatPassword(Constants.VALID_PASSWORD)
            .waitForAvatars()
            .selectAvatar(Constants.AVATAR_INDEX)
            .checkAvatarIsSelected(Constants.AVATAR_INDEX)
            .submitRegistration()

        homeSteps.checkHomeScreenIsDisplayed()
    }
}
