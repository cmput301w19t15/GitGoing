package com.example.cmput301w19t15;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.cmput301w19t15.Activity.MainActivity;

import java.util.Random;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;

/**
 * test my book - not yet implemented
 */
@RunWith(AndroidJUnit4.class)
public class TransactionTest extends ActivityTestRule<MainActivity> {
    public TransactionTest() {
        super(MainActivity.class);
    }

    private Solo solo;
    private String generatedString;
    private String Email;


    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
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
        solo.clickOnView(solo.getCurrentActivity().findViewById(
                R.id.logout_button));
        assertTrue(solo.waitForText("Forgot Password", 1, 2000));
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.register_button));
        Email = givenUsingPlainJava_whenGeneratingRandomStringUnbounded_thenCorrect();
        System.out.println(Email);
        solo.enterText((EditText) solo.getView(R.id.email), " ");
        solo.enterText((EditText) solo.getView(R.id.email), Email);
        solo.enterText((EditText) solo.getView(R.id.password), "password");
        solo.enterText((EditText) solo.getView(R.id.name), "test name");
        solo.enterText((EditText) solo.getView(R.id.pass), "1112223333");
        solo.clickOnView(solo.getCurrentActivity().findViewById(
                R.id.register_button));
        assertTrue(solo.waitForText("Find Books", 1, 2000));
        return Email;
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

    public void MakeRequest(String Owner) {
        solo.clickOnText("Find Books");
        assertTrue(solo.waitForText("Search", 1, 2000));
        solo.enterText((EditText) solo.getView(R.id.searchTextView),Owner);
        solo.clickOnButton("Search");
        solo.clickInRecyclerView(0);
        assertTrue(solo.waitForText(Owner, 1, 2000));
        solo.clickOnButton("Request Book");
        solo.goBack();
        assertTrue(solo.waitForText("Find Books", 1, 2000));

    }
    public void VerifyRequest(String Owner) {
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.my_requests));
        assertTrue(solo.waitForText(Owner, 1, 2000));
        solo.goBack();
        assertTrue(solo.waitForText("Find Books", 1, 2000));

    }
    public void Login(String Owner) {
        solo.clickOnView(solo.getCurrentActivity().findViewById(
                R.id.logout_button));
        assertTrue(solo.waitForText("Forgot Password", 1, 2000));
        solo.enterText((EditText) solo.getView(R.id.email),Owner);
        solo.enterText((EditText) solo.getView(R.id.password),"password");
        solo.clickOnView( solo.getCurrentActivity().findViewById(
                R.id.login_button));
        assertTrue(solo.waitForText("Find Books", 1, 2000));
    }
    public void ViewRequest(String Requester) {
        solo.clickOnView(solo.getCurrentActivity().findViewById(
                R.id.notify));
        assertTrue(solo.waitForText("Notifications", 1, 2000));
        solo.clickInRecyclerView(0);
        assertTrue(solo.waitForText("test book", 1, 2000));
        assertTrue(solo.waitForText("123456789", 1, 2000));
        assertTrue(solo.waitForText(Requester, 1, 2000));
        solo.goBack();
        assertTrue(solo.waitForText("Notifications", 1, 2000));
        solo.goBack();
    }
    public void AcceptTheRequest(){
        solo.clickOnView(solo.getCurrentActivity().findViewById(
                R.id.notify));
        assertTrue(solo.waitForText("Notifications", 1, 2000));
        solo.clickInRecyclerView(0);
        assertTrue(solo.waitForText("test book", 1, 2000));
        solo.clickOnView(solo.getCurrentActivity().findViewById(
                R.id.accept_button));
        assertTrue(solo.waitForText("Notifications", 1, 2000));
        solo.goBack();
    }
    @Test
    public void TestTransaction() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        String emailOwner = CreateAccount();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        AddBook();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        String emailRequester = CreateAccount();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        MakeRequest(emailOwner);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        VerifyRequest(emailOwner);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        Login(emailOwner);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        ViewRequest(emailRequester);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        AcceptTheRequest();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }
}

