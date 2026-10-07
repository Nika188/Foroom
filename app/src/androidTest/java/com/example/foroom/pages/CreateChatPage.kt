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

class CreateChatPage {
    val chatNameInput: Matcher<View> = withId(R.id.chatNameInput)
    val chatImageChooser: Matcher<View> = withId(R.id.chatImageChooser)
    val createChatButton: Matcher<View> = withId(R.id.createChatButton)

    val chatNameEditText: Matcher<View> =
        allOf(withId(DesignR.id.inputEditText), isDescendantOfA(chatNameInput))
    private val messageInput: Matcher<View> = withId(R.id.messageInput)
    val createdChatCloseButton: Matcher<View> =
        allOf(withId(R.id.closeButton), hasSibling(messageInput))
    val createdChatName: Matcher<View> = allOf(
        withId(DesignR.id.chatNameTextView),
        isDescendantOfA(allOf(withId(R.id.chatHeaderView), hasSibling(messageInput)))
    )

    fun chatImageAt(index: Int): Matcher<View> =
        object : BoundedMatcher<View, ImageChooserItemView>(ImageChooserItemView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("chat image at index $index")
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
