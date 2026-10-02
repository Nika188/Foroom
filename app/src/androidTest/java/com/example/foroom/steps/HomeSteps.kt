package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.waitUntilMatches
import com.example.foroom.data.Constants
import com.example.foroom.pages.HomePage

class HomeSteps {
    private val homePage = HomePage()

    fun checkHomeScreenIsDisplayed() = apply {
        with(homePage) {
            listOf(homeContainer, navBar).forEach { element ->
                onView(element).waitUntilMatches(isDisplayed(), Constants.TIMEOUT_SEC)
            }
        }
    }
}
