package com.example.wassim.bakingapp.Activities;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.wassim.bakingapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest6 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

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

    @Test
    public void mainActivityTest6() {

        // test for TwoPaneLayout
        if (onView(allOf(withId(R.id.two_pane_activity_master_list))) != null) {
            DataInteraction linearLayout = onData(anything())
                    .inAdapterView(allOf(withId(R.id.main_grid_view),
                            childAtPosition(
                                    withClassName(is("android.widget.LinearLayout")),
                                    0)))
                    .atPosition(0);
            linearLayout.perform(click());

            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.master_recycler_view),
                            childAtPosition(
                                    withId(R.id.recipe_detail_fragment),
                                    1)));
            recyclerView.perform(actionOnItemAtPosition(0, click()));

            ViewInteraction recyclerView2 = onView(
                    allOf(withId(R.id.master_recycler_view),
                            childAtPosition(
                                    withId(R.id.recipe_detail_fragment),
                                    1)));
            recyclerView2.perform(actionOnItemAtPosition(3, click()));

            ViewInteraction recyclerView3 = onView(
                    allOf(withId(R.id.master_recycler_view),
                            childAtPosition(
                                    withId(R.id.recipe_detail_fragment),
                                    1)));
            recyclerView3.perform(actionOnItemAtPosition(5, click()));

            ViewInteraction cardView = onView(
                    allOf(withId(R.id.ingredients_card),
                            childAtPosition(
                                    allOf(withId(R.id.recipe_detail_fragment),
                                            childAtPosition(
                                                    withId(R.id.two_pane_activity_master_list),
                                                    0)),
                                    0),
                            isDisplayed()));
            cardView.perform(click());

            // test for hand held device
        } else {
            ViewInteraction textView = onView(
                    allOf(withId(R.id.step_description), withText("Brownies"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.cv),
                                            0),
                                    0),
                            isDisplayed()));
            textView.check(matches(withText("Brownies")));

            DataInteraction linearLayout = onData(anything())
                    .inAdapterView(allOf(withId(R.id.main_grid_view),
                            childAtPosition(
                                    withClassName(is("android.widget.LinearLayout")),
                                    0)))
                    .atPosition(0);
            linearLayout.perform(click());

            ViewInteraction cardView = onView(
                    allOf(withId(R.id.ingredients_card),
                            childAtPosition(
                                    allOf(withId(R.id.recipe_detail_fragment),
                                            childAtPosition(
                                                    withClassName(is("android.support.constraint.ConstraintLayout")),
                                                    0)),
                                    0),
                            isDisplayed()));
            cardView.perform(click());

            ViewInteraction textView2 = onView(
                    allOf(withId(R.id.id), withText("5) 5 TBLSP vanilla"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.list),
                                            4),
                                    0),
                            isDisplayed()));
            textView2.check(matches(withText("5) 5 TBLSP vanilla")));

            pressBack();

            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.master_recycler_view),
                            childAtPosition(
                                    withId(R.id.recipe_detail_fragment),
                                    1)));
            recyclerView.perform(actionOnItemAtPosition(0, click()));

            ViewInteraction button = onView(
                    allOf(withId(R.id.next_recipe_button), withText("next"),
                            childAtPosition(
                                    allOf(withId(R.id.navigation_layout),
                                            childAtPosition(
                                                    withId(R.id.fragment_details_id),
                                                    2)),
                                    1),
                            isDisplayed()));
            button.perform(click());

            ViewInteraction button2 = onView(
                    allOf(withId(R.id.next_recipe_button), withText("next"),
                            childAtPosition(
                                    allOf(withId(R.id.navigation_layout),
                                            childAtPosition(
                                                    withId(R.id.fragment_details_id),
                                                    2)),
                                    1),
                            isDisplayed()));
            button2.perform(click());

            ViewInteraction button3 = onView(
                    allOf(withId(R.id.next_recipe_button), withText("next"),
                            childAtPosition(
                                    allOf(withId(R.id.navigation_layout),
                                            childAtPosition(
                                                    withId(R.id.fragment_details_id),
                                                    2)),
                                    1),
                            isDisplayed()));
            button3.perform(click());

            ViewInteraction button4 = onView(
                    allOf(withId(R.id.previous_recipe_button), withText("previous"),
                            childAtPosition(
                                    allOf(withId(R.id.navigation_layout),
                                            childAtPosition(
                                                    withId(R.id.fragment_details_id),
                                                    2)),
                                    0),
                            isDisplayed()));
            button4.perform(click());

            ViewInteraction button5 = onView(
                    allOf(withId(R.id.previous_recipe_button), withText("previous"),
                            childAtPosition(
                                    allOf(withId(R.id.navigation_layout),
                                            childAtPosition(
                                                    withId(R.id.fragment_details_id),
                                                    2)),
                                    0),
                            isDisplayed()));
            button5.perform(click());

            ViewInteraction button6 = onView(
                    allOf(withId(R.id.previous_recipe_button), withText("previous"),
                            childAtPosition(
                                    allOf(withId(R.id.navigation_layout),
                                            childAtPosition(
                                                    withId(R.id.fragment_details_id),
                                                    2)),
                                    0),
                            isDisplayed()));
            button6.perform(click());

            ViewInteraction imageButton = onView(
                    allOf(withId(R.id.exo_pause), withContentDescription("Pause"),
                            childAtPosition(
                                    childAtPosition(
                                            withClassName(is("android.widget.LinearLayout")),
                                            0),
                                    1),
                            isDisplayed()));
            imageButton.perform(click());

            ViewInteraction imageButton2 = onView(
                    allOf(withId(R.id.exo_play), withContentDescription("Play"),
                            childAtPosition(
                                    childAtPosition(
                                            withClassName(is("android.widget.LinearLayout")),
                                            0),
                                    0),
                            isDisplayed()));
            imageButton2.perform(click());

            ViewInteraction imageButton3 = onView(
                    allOf(withId(R.id.exo_pause), withContentDescription("Pause"),
                            childAtPosition(
                                    childAtPosition(
                                            withClassName(is("android.widget.LinearLayout")),
                                            0),
                                    1),
                            isDisplayed()));
            imageButton3.perform(click());
        }
    }
}
