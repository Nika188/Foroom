package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.foroom.Helper.input
import com.example.foroom.Helper.tap
import com.example.foroom.Helper.waitUntilMatches
import com.example.foroom.data.Constants
import com.example.foroom.pages.LoginPage
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.isEmptyString
import org.hamcrest.Matchers.not
import org.koin.core.context.GlobalContext

class LoginSteps {
    private val loginPage = LoginPage()

    fun clearSavedSession() = apply {
        runBlocking { GlobalContext.get().get<ForoomUserDataStore>().clearUserData() }
    }

    fun checkLoginScreenIsDisplayed() = apply {
        with(loginPage) {
            listOf(userNameInput, passwordInput, logInButton, signUpButton).forEach { element ->
                onView(element).waitUntilMatches(isDisplayed(), Constants.TIMEOUT_SEC)
            }
        }
    }

    fun enterUserName(userName: String) = apply {
        onView(loginPage.userNameEditText).input(userName)
    }

    fun enterPassword(password: String) = apply {
        onView(loginPage.passwordEditText).input(password)
    }

    fun submitLogin() = apply {
        onView(loginPage.logInButton).tap()
    }

    fun openRegistration() = apply {
        onView(loginPage.signUpButton).tap()
    }

    fun checkUserNameErrorIsDisplayed() = apply {
        onView(loginPage.userNameError)
            .waitUntilMatches(allOf(isDisplayed(), withText(not(isEmptyString()))), Constants.TIMEOUT_SEC)
    }

    fun checkPasswordErrorIsDisplayed() = apply {
        onView(loginPage.passwordError)
            .waitUntilMatches(allOf(isDisplayed(), withText(not(isEmptyString()))), Constants.TIMEOUT_SEC)
    }
}
