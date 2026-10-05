package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.util.TreeIterables
import com.alternator.foroom.R
import com.example.design_system.components.image_chooser.ImageChooserItemView
import com.example.design_system.components.image_chooser.ImageChooserListView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR

class RegistrationPage {
    val repeatPasswordInput: Matcher<View> = withId(R.id.repeatPasswordInput)
    val avatarList: Matcher<View> = withId(R.id.listView)

    // userNameInput, passwordInput and signUpButton ids are reused on the log in screen,
    // so they are scoped to the screen which contains the repeat password input
    val userNameInput: Matcher<View> =
        allOf(withId(R.id.userNameInput), hasSibling(repeatPasswordInput))
    val passwordInput: Matcher<View> =
        allOf(withId(R.id.passwordInput), hasSibling(repeatPasswordInput))
    val signUpButton: Matcher<View> =
        allOf(withId(R.id.signUpButton), hasSibling(repeatPasswordInput))

    val userNameEditText: Matcher<View> =
        allOf(withId(DesignR.id.inputEditText), isDescendantOfA(userNameInput))
    val passwordEditText: Matcher<View> =
        allOf(withId(DesignR.id.inputEditText), isDescendantOfA(passwordInput))
    val repeatPasswordEditText: Matcher<View> =
        allOf(withId(DesignR.id.inputEditText), isDescendantOfA(repeatPasswordInput))

    // avatar items have no ids, so they are located by their position inside the avatar list
    fun avatarAt(index: Int): Matcher<View> =
        object : BoundedMatcher<View, ImageChooserItemView>(ImageChooserItemView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("avatar at index $index")
            }

            override fun matchesSafely(item: ImageChooserItemView): Boolean {
                var parent = item.parent
                while (parent != null && parent !is ImageChooserListView) parent = parent.parent
                val list = parent as? ImageChooserListView ?: return false

                return TreeIterables.depthFirstViewTraversal(list)
                    .filterIsInstance<ImageChooserItemView>()
                    .indexOf(item) == index
            }
        }
}
