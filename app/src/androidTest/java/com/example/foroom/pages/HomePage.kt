package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import org.hamcrest.Matcher

class HomePage {
    val homeContainer: Matcher<View> = withId(R.id.homeContainer)
    val navBar: Matcher<View> = withId(R.id.navBar)
}
