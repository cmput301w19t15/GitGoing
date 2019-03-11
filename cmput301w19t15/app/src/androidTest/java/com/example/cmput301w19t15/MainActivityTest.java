package com.example.cmput301w19t15;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends ActivityTestRule<MainActivity> {
    public MainActivityTest(){
        super(MainActivity.class);
    }

    private Solo solo;
    private String generatedString;
    private String Email;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    public String givenUsingPlainJava_whenGeneratingRandomStringUnbounded_thenCorrect() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        generatedString = buffer.toString();
        generatedString = generatedString + "@Email.com";
        System.out.println(generatedString);
        return generatedString;
    }

    public void CreateAccount() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView( solo.getCurrentActivity().findViewById(
                R.id.logout_button));
        assertTrue(solo.waitForText("Email", 1, 2000));
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.register_button));
        Email = givenUsingPlainJava_whenGeneratingRandomStringUnbounded_thenCorrect();
        System.out.println(Email);
        solo.enterText((EditText) solo.getView(R.id.email),"test@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.email),Email);
        solo.enterText((EditText) solo.getView(R.id.password),"password");
        solo.enterText((EditText) solo.getView(R.id.name),"test name");
        solo.enterText((EditText) solo.getView(R.id.pass),"1112223333");
        solo.clickOnView( solo.getCurrentActivity().findViewById(
                R.id.register_button));
        assertTrue(solo.waitForText("Find Books", 1, 2000));


    }
    @Test
    public void TestCreateAccount() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        CreateAccount();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    public void getToRequest() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Find Books");
        assertTrue(solo.waitForText("Search", 1, 2000));
        solo.assertCurrentActivity("Wrong Activity", FindBooks.class);
        solo.enterText((EditText) solo.getView(R.id.searchTextView),"anjesh");
        solo.clickOnButton("Search");
        solo.clickInRecyclerView(0);
        assertTrue(solo.waitForText("Anjesh weird", 1, 2000));
    }

    @Test
    public void testRequest() {
        getToRequest();
        solo.assertCurrentActivity("Wrong Activity", CreateRequest.class);
        solo.clickOnButton("Request Book");
        solo.goBack();
        try{
            wait(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        //assertTrue(solo.waitForText("My REQUESTS", 1, 2000));
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.my_requests));
        solo.assertCurrentActivity("Wrong Activity", RequestedBookList.class);
        assertTrue(solo.waitForText("Anjesh weird", 1, 2000));
    }


    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}