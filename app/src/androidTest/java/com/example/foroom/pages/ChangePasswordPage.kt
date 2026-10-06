package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR

class ChangePasswordPage {
    private val sheetContent: Matcher<View> = withId(DesignR.id.contentContainer)
    val passwordInput: Matcher<View> =
        allOf(withId(R.id.passwordInput), isDescendantOfA(sheetContent))
    val repeatPasswordInput: Matcher<View> =
        allOf(withId(R.id.repeatPasswordInput), isDescendantOfA(sheetContent))

    val passwordEditText: Matcher<View> =
        allOf(withId(DesignR.id.inputEditText), isDescendantOfA(passwordInput))
    val repeatPasswordEditText: Matcher<View> =
        allOf(withId(DesignR.id.inputEditText), isDescendantOfA(repeatPasswordInput))

    val confirmButton: Matcher<View> = withId(DesignR.id.actionButton)
}
