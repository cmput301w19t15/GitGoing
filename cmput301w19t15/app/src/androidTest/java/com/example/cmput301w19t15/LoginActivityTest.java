package com.example.cmput301w19t15;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest extends ActivityTestRule<LoginActivity> {
    public LoginActivityTest(){
        super(LoginActivity.class);
    }

    private Solo solo;

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class, true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * test login with user
     */
    @Test
    public void checkView() {
        onView(withId(R.id.email)).perform(clearText(),typeText("test@test.com"));
        onView(withId(R.id.password)).perform(clearText(),typeText("testtest"));
    }

    /**
     * check if the user is logged in
     */
    @Test
    public  void checkLogin(){
        onView(withId(R.id.email)).perform(clearText(),typeText("test"));

        solo.assertCurrentActivity("Wrong Activity",LoginActivity.class);

        solo.clickOnButton("Login");

        solo.clearEditText((EditText) solo.getView(R.id.register_button));


    }

    /**
     * test user has successuly logged in
     */
    @Test
    public void checkEntry(){
        LoginActivity activity = (LoginActivity) solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity",LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.email),"test@email");
        solo.enterText((EditText) solo.getView(R.id.password),"test");

        solo.clickOnButton("Login");

        assertTrue(solo.waitForText("Test Login!",1,2000));
        /**final ListView oldtweetlist = activity.getOldTweetsList();

        Tweet tweet = (Tweet) oldtweetlist.getItemAtPosition(0);

        assertEquals("Test Tweet!", tweet.getMessage());**/

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);

        assertTrue(solo.waitForText("Test Tweet!"));

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}