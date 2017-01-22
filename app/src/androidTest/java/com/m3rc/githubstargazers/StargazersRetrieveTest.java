package com.m3rc.githubstargazers;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.m3rc.githubstargazers.activity.HomeActivity;
import com.m3rc.githubstargazers.activity.StargazersActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.m3rc.githubstargazers.utils.TestUtils.withRecyclerView;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class StargazersRetrieveTest {

    @Rule
    public final ActivityTestRule<StargazersActivity> stargazersActivity =
            new ActivityTestRule<>(StargazersActivity.class, false, false);

    private final int recyclerViewId = R.id.stargazers_recycler_view;

    @Test
    public void loadSquareRetrofit() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        Intent intent = new Intent(context, StargazersActivity.class);
        intent.putExtra(HomeActivity.OWNER_EXTRA, "square");
        intent.putExtra(HomeActivity.REPO_EXTRA, "retrofit");
        stargazersActivity.launchActivity(intent);

        onView(withId(recyclerViewId)).perform(scrollToPosition(5));

        onView(withRecyclerView(recyclerViewId).atPosition(5)).check(matches(isDisplayed()));
    }

    @Test
    public void loadGoogleGson() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        Intent intent = new Intent(context, StargazersActivity.class);
        intent.putExtra(HomeActivity.OWNER_EXTRA, "google");
        intent.putExtra(HomeActivity.REPO_EXTRA, "gson");
        stargazersActivity.launchActivity(intent);

        onView(withId(recyclerViewId)).perform(scrollToPosition(10));

        onView(withRecyclerView(recyclerViewId).atPosition(10)).check(matches(isDisplayed()));
    }
}
