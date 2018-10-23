package com.abhi.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ABHI on 10/23/2018.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecyclerViewItem_openRecipeDetailActivity() {


        //clicking the first element in the recycler view
        onView(withId(R.id.recipe_reclerview_id)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withId(R.id.recipe_reclerview_id)).perform(RecyclerViewActions.scrollToPosition(1)).perform(click());


        //checking for the id of recyclerview from recipe detail fragment is displayed which makes sure that fragment has been loaded
        onView(withId(R.id.recipe_reclerview_id)).check(matches(isDisplayed()));


    }

}
