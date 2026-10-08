package com.example.foroom.steps

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.design_system.components.image_chooser.ImageChooserListView
import com.example.foroom.Helper.input
import com.example.foroom.Helper.tap
import com.example.foroom.Helper.waitUntilMatches
import com.example.foroom.data.Constants
import com.example.foroom.domain.model.request.LogInRequest
import com.example.foroom.domain.model.request.RegistrationRequest
import com.example.foroom.domain.usecase.LogInUserUseCase
import com.example.foroom.domain.usecase.RegisterUserUseCase
import com.example.foroom.pages.RegistrationPage
import kotlinx.coroutines.runBlocking
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.koin.core.context.GlobalContext

class RegistrationSteps {
    private val registrationPage = RegistrationPage()

    fun createAccount(userName: String, password: String, avatarId: Int) = apply {
        runBlocking {
            GlobalContext.get().get<RegisterUserUseCase>()(
                RegistrationRequest(userName, password, avatarId)
            )
        }
    }

    fun ensureAccountExists(userName: String, password: String, avatarId: Int) = apply {
        runBlocking {
            runCatching {
                GlobalContext.get().get<LogInUserUseCase>()(LogInRequest(userName, password))
            }.onFailure {
                createAccount(userName, password, avatarId)
            }
        }
    }

    fun checkRegistrationScreenIsDisplayed() = apply {
        with(registrationPage) {
            listOf(userNameInput, passwordInput, repeatPasswordInput, avatarList, signUpButton)
                .forEach { element ->
                    onView(element).waitUntilMatches(isDisplayed(), Constants.TIMEOUT_SEC)
                }
        }
    }

    fun enterUserName(userName: String) = apply {
        onView(registrationPage.userNameEditText).input(userName)
    }

    fun enterPassword(password: String) = apply {
        onView(registrationPage.passwordEditText).input(password)
    }

    fun enterRepeatPassword(password: String) = apply {
        onView(registrationPage.repeatPasswordEditText).input(password)
    }

    fun waitForAvatars() = apply {
        onView(registrationPage.avatarList)
            .waitUntilMatches(avatarListState("avatars are loaded") { list ->
                list.isChoosingEnabled && list.images.isNotEmpty()
            }, Constants.TIMEOUT_SEC)
    }

    fun selectAvatar(index: Int) = apply {
        onView(registrationPage.avatarAt(index)).tap()
    }

    fun checkAvatarIsSelected(index: Int) = apply {
        onView(registrationPage.avatarList)
            .waitUntilMatches(avatarListState("selected avatar index is $index") { list ->
                list.selectedIndex == index
            }, Constants.TIMEOUT_SEC)
    }

    fun submitRegistration() = apply {
        onView(registrationPage.signUpButton).tap()
    }

    private fun avatarListState(
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
