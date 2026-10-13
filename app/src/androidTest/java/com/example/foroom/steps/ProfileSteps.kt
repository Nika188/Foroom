package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.foroom.Helper.input
import com.example.foroom.Helper.tap
import com.example.foroom.Helper.waitUntilMatches
import com.example.foroom.data.Constants
import com.example.foroom.pages.ChangeLanguagePage
import com.example.foroom.pages.ChangePasswordPage
import com.example.foroom.pages.ProfilePage
import org.hamcrest.Matchers.allOf

class ProfileSteps {
    private val profilePage = ProfilePage()
    private val changePasswordPage = ChangePasswordPage()
    private val changeLanguagePage = ChangeLanguagePage()

    fun checkProfileScreenIsDisplayed() = apply {
        with(profilePage) {
            listOf(changePasswordItem, changeLanguageItem, signOutItem).forEach { element ->
                onView(element).waitUntilMatches(isDisplayed(), Constants.TIMEOUT_SEC)
            }
        }
    }

    fun openChangePassword() = apply {
        onView(profilePage.changePasswordItem).tap()
    }

    fun enterNewPassword(password: String) = apply {
        onView(changePasswordPage.passwordEditText).input(password)
    }

    fun enterRepeatNewPassword(password: String) = apply {
        onView(changePasswordPage.repeatPasswordEditText).input(password)
    }

    fun confirmPasswordChange() = apply {
        onView(changePasswordPage.confirmButton).tap()
    }

    fun openChangeLanguage() = apply {
        onView(profilePage.changeLanguageItem).tap(Constants.TIMEOUT_SEC)
    }

    fun selectGeorgianLanguage() = apply {
        onView(changeLanguagePage.languageButtonGeo).tap(Constants.TIMEOUT_SEC)
    }

    fun selectEnglishLanguage() = apply {
        onView(changeLanguagePage.languageButtonEng).tap(Constants.TIMEOUT_SEC)
    }

    fun checkChangeLanguageLabel(expectedLabel: String) = apply {
        onView(profilePage.changeLanguageLabel)
            .waitUntilMatches(allOf(isDisplayed(), withText(expectedLabel)), Constants.TIMEOUT_SEC)
    }

    fun signOut() = apply {
        onView(profilePage.signOutItem).tap()
    }
}
