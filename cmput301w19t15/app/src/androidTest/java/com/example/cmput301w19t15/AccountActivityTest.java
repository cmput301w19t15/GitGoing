package com.example.cmput301w19t15;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;

/**
 * test login activity
 */
@RunWith(AndroidJUnit4.class)
public class AccountActivityTest extends ActivityTestRule<LoginActivity> {
    public AccountActivityTest(){
        super(LoginActivity.class);
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
        generatedString = generatedString + "@email.com";
        System.out.println(generatedString);
        return generatedString;
    }

    public String CreateAccount() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView( solo.getCurrentActivity().findViewById(
                R.id.logout_button));
        assertTrue(solo.waitForText("Forgot Password", 1, 2000));
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.register_button));
        Email = givenUsingPlainJava_whenGeneratingRandomStringUnbounded_thenCorrect();
        System.out.println(Email);
        solo.enterText((EditText) solo.getView(R.id.email)," ");
        solo.enterText((EditText) solo.getView(R.id.email),Email);
        solo.enterText((EditText) solo.getView(R.id.password),"password");
        solo.enterText((EditText) solo.getView(R.id.name),"test name");
        solo.enterText((EditText) solo.getView(R.id.pass),"1112223333");
        solo.clickOnView( solo.getCurrentActivity().findViewById(
                R.id.register_button));
        assertTrue(solo.waitForText("Find Books", 1, 2000));
        return Email;

    }
    public void CheckAccount(String username) {
        solo.clickOnView( solo.getCurrentActivity().findViewById(
                R.id.profile));
        assertTrue(solo.waitForText("User Rating", 1, 2000));
        EditText name = (EditText) solo.getView(R.id.name);
        assertEquals("incorrect name", "test name", name.getText().toString());
        EditText email = (EditText) solo.getView(R.id.email);
        assertEquals("incorrect email", username, email.getText().toString());
        EditText phone = (EditText) solo.getView(R.id.phone);
        assertEquals("incorrect phone", "1112223333", phone.getText().toString());
        solo.clickOnView( solo.getCurrentActivity().findViewById(
                R.id.cancel));
        assertTrue(solo.waitForText("Find Books", 1, 2000));


    }

    public void ChangePassword(String username) {
        solo.clickOnView( solo.getCurrentActivity().findViewById(
                R.id.profile));
        assertTrue(solo.waitForText("User Rating", 1, 2000));
        solo.enterText((EditText) solo.getView(R.id.pass),"password");
        solo.enterText((EditText) solo.getView(R.id.newPassword),"newpassword");
        solo.clickOnView( solo.getCurrentActivity().findViewById(
                R.id.save));
        assertTrue(solo.waitForText("Find Books", 1, 2000));
        EditText name = (EditText) solo.getView(R.id.name);
        solo.clickOnView( solo.getCurrentActivity().findViewById(
                R.id.logout_button));
        assertTrue(solo.waitForText("Forgot Password", 1, 2000));
        solo.enterText((EditText) solo.getView(R.id.email),username);
        solo.enterText((EditText) solo.getView(R.id.password),"newpassword");
        solo.clickOnView( solo.getCurrentActivity().findViewById(
                R.id.login_button));
        assertTrue(solo.waitForText("Find Books", 1, 2000));

    }
    public void AddBook(){
        solo.clickOnView( solo.getCurrentActivity().findViewById(
                R.id.my_books));
        assertTrue(solo.waitForText("Filter By", 1, 2000));
        solo.clickOnView( solo.getCurrentActivity().findViewById(
                R.id.add_book));
        assertTrue(solo.waitForText("Enter Book Title", 1, 2000));
        solo.enterText((EditText) solo.getView(R.id.booktitle),"test book");
        solo.enterText((EditText) solo.getView(R.id.bookAuthor),"test author");
        solo.enterText((EditText) solo.getView(R.id.isbn),"123456789");
        solo.clickOnView( solo.getCurrentActivity().findViewById(R.id.addBook));
        assertTrue(solo.waitForText("test book", 1, 2000));
        solo.goBack();
        assertTrue(solo.waitForText("Find Books", 1, 2000));
    }
    public void CheckBook(){
        solo.clickOnView( solo.getCurrentActivity().findViewById(
                R.id.my_books));
        assertTrue(solo.waitForText("Filter By", 1, 2000));
        solo.clickInRecyclerView(0);
        assertTrue(solo.waitForText("Title:", 1, 2000));
        EditText title = (EditText) solo.getView(R.id.editMyBookTitle);
        assertEquals("incorrect title", "test book", title.getText().toString());
        EditText author = (EditText) solo.getView(R.id.editMyBookAuthor);
        assertEquals("incorrect author", "test author", author.getText().toString());
        EditText isbn = (EditText) solo.getView(R.id.editMyBookISBN);
        assertEquals("incorrect isbn", "123456789", isbn.getText().toString());
        solo.clickOnView( solo.getCurrentActivity().findViewById(R.id.cancel));
        assertTrue(solo.waitForText("test book", 1, 2000));
        solo.goBack();
        assertTrue(solo.waitForText("Find Books", 1, 2000));
    }
    public void EditBook(){
        solo.clickOnView( solo.getCurrentActivity().findViewById(
                R.id.my_books));
        assertTrue(solo.waitForText("Filter By", 1, 2000));
        solo.clickInRecyclerView(0);
        assertTrue(solo.waitForText("Title:", 1, 2000));
        solo.clearEditText((EditText) solo.getView(R.id.editMyBookTitle));
        solo.clearEditText((EditText) solo.getView(R.id.editMyBookAuthor));
        solo.clearEditText((EditText) solo.getView(R.id.editMyBookISBN));
        solo.enterText((EditText) solo.getView(R.id.editMyBookTitle),"new test book");
        solo.enterText((EditText) solo.getView(R.id.editMyBookAuthor),"new test author");
        solo.enterText((EditText) solo.getView(R.id.editMyBookISBN),"987654321");
        solo.clickOnView( solo.getCurrentActivity().findViewById(
                R.id.updateBook));
        assertTrue(solo.waitForText("Find Books", 1, 2000));
        solo.clickInRecyclerView(0);
        assertTrue(solo.waitForText("Title:", 1, 2000));
        EditText title = (EditText) solo.getView(R.id.editMyBookTitle);
        assertEquals("incorrect title", "new test book", title.getText().toString());
        EditText author = (EditText) solo.getView(R.id.editMyBookAuthor);
        assertEquals("incorrect author", "new test author", author.getText().toString());
        EditText isbn = (EditText) solo.getView(R.id.editMyBookISBN);
        assertEquals("incorrect isbn", "987654321", isbn.getText().toString());
        solo.clickOnView( solo.getCurrentActivity().findViewById(R.id.cancel));
        assertTrue(solo.waitForText("test book", 1, 2000));
        solo.goBack();
        assertTrue(solo.waitForText("Find Books", 1, 2000));
    }
    /**
     * test creating account
     */
    @Test
    public void TestAccount() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        String email = CreateAccount();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        CheckAccount(email);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        ChangePassword(email);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        AddBook();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        CheckBook();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        EditBook();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }


}