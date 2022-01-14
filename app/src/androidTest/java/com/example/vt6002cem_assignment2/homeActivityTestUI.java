package com.example.vt6002cem_assignment2;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class homeActivityTestUI {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void homeActivityTest() throws InterruptedException {

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.menu_icon),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        Thread.sleep(5000);
        appCompatImageView.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.nav_home),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.navigation_view),
                                                0)),
                                1),
                        isDisplayed()));
        Thread.sleep(5000);
        navigationMenuItemView.perform(click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.menu_icon),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        Thread.sleep(5000);
        appCompatImageView2.perform(click());

        ViewInteraction navigationMenuItemView2 = onView(
                allOf(withId(R.id.nav_product),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.navigation_view),
                                                0)),
                                2),
                        isDisplayed()));
        Thread.sleep(5000);
        navigationMenuItemView2.perform(click());

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.menu_icon),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        Thread.sleep(5000);
        appCompatImageView3.perform(click());

        ViewInteraction navigationMenuItemView3 = onView(
                allOf(withId(R.id.nav_record),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.navigation_view),
                                                0)),
                                3),
                        isDisplayed()));
        Thread.sleep(5000);
        navigationMenuItemView3.perform(click());

        ViewInteraction appCompatImageView4 = onView(
                allOf(withId(R.id.menu_icon),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        Thread.sleep(5000);
        appCompatImageView4.perform(click());

        ViewInteraction navigationMenuItemView4 = onView(
                allOf(withId(R.id.nav_login),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.navigation_view),
                                                0)),
                                6),
                        isDisplayed()));
        Thread.sleep(5000);
        navigationMenuItemView4.perform(click());

        ViewInteraction appCompatImageView5 = onView(
                allOf(withId(R.id.menu_icon),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        Thread.sleep(5000);
        appCompatImageView5.perform(click());

        ViewInteraction navigationMenuItemView5 = onView(
                allOf(withId(R.id.nav_setting),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.navigation_view),
                                                0)),
                                7),
                        isDisplayed()));
        Thread.sleep(5000);
        navigationMenuItemView5.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(0);
        Thread.sleep(5000);
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatImageView6 = onView(
                allOf(withId(R.id.menu_icon),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        Thread.sleep(5000);
        appCompatImageView6.perform(click());

        ViewInteraction navigationMenuItemView6 = onView(
                allOf(withId(R.id.nav_setting),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.navigation_view),
                                                0)),
                                7),
                        isDisplayed()));
        Thread.sleep(5000);
        navigationMenuItemView6.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(1);
        Thread.sleep(5000);
        appCompatCheckedTextView2.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
