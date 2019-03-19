package com.example.cmput301w19t15;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * test my book - not yet implemented
 */
@RunWith(AndroidJUnit4.class)
public class MyBooksTest {

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    //onView(withId(R.id.recylcerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
}