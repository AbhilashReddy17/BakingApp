package com.abhi.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anyOf;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    public ActivityTestRule<RecipeDetailActivity> mActivityTestRule = new ActivityTestRule<>(RecipeDetailActivity.class);

    @Test
    public void clickRecyclerViewItem_openRecipeDetailFragment_or_openRecipeStepFragment_aswell() {


        //checking the RecipeDetailFragment is actually displayed or not

        onView(withId(R.id.recipe_reclerview_id)).check(matches(isDisplayed()));

        //clicking the first element in the recycler view
        onView(withId(R.id.recipe_reclerview_id)).perform(RecyclerViewActions.scrollToPosition(0)).perform(click());


        //checking for the id of recyclerview from recipe detail fragment or recipe step fragment  is displayed which makes sure that fragment has been loaded
        onView(anyOf(withId(R.id.recipe_reclerview_id), withId(R.id.playerView))).check(matches(isDisplayed()));

    }

}
