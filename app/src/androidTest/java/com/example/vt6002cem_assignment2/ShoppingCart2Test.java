package com.example.vt6002cem_assignment2;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

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

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ShoppingCart2Test {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shoppingCart2Test() throws InterruptedException {
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
                allOf(withId(R.id.nav_login),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.navigation_view),
                                                0)),
                                6),
                        isDisplayed()));
        Thread.sleep(5000);
        navigationMenuItemView.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button_signup), withText("Don't have an account? Sign up"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                6),
                        isDisplayed()));
        Thread.sleep(5000);
        appCompatButton.perform(click());

        Thread.sleep(5000);
        onView(withId(R.id.fg_email)).perform(replaceText("tsefungtam@gmail.com"));
        Thread.sleep(5000);
        onView(withId(R.id.et_username)).perform(replaceText("Chairmantim"));
        Thread.sleep(5000);
        onView(withId(R.id.et_phone)).perform(replaceText("23456789"));
        Thread.sleep(5000);
        onView(withId(R.id.et_password)).perform(replaceText("111111"));
        Thread.sleep(5000);
        onView(withId(R.id.et_confirm_password)).perform(replaceText("111111"));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button_signin), withText("Sign up"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                7),
                        isDisplayed()));

        appCompatButton2.perform(click());
        Thread.sleep(10000);


        Thread.sleep(5000);
        onView(withId(R.id.fg_email)).perform(replaceText("chairmanchairman01@gmail.com"));
        Thread.sleep(5000);
        onView(withId(R.id.et_password)).perform(replaceText("722888"));



        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button_signin), withText("Sign in"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                5),
                        isDisplayed()));
        Thread.sleep(5000);
        appCompatButton3.perform(click());

        Thread.sleep(5000);

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.cart),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageView2.perform(click());
        Thread.sleep(5000);

        ViewInteraction appCompatButton4 = onView(
                allOf(withText("Confirm Order"),
                        childAtPosition(
                                allOf(withId(R.id.Liner),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatButton4.perform(click());
        Thread.sleep(5000);

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(android.R.id.button2), withText("No"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                2)));
        appCompatButton5.perform(scrollTo(), click());
        Thread.sleep(5000);

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.back_icon),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView3.perform(click());
        Thread.sleep(5000);

        ViewInteraction appCompatImageView4 = onView(
                allOf(withId(R.id.menu_icon),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView4.perform(click());
        Thread.sleep(5000);

        ViewInteraction navigationMenuItemView2 = onView(
                allOf(withId(R.id.nav_logout),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.navigation_view),
                                                0)),
                                7),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());
        Thread.sleep(5000);
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
