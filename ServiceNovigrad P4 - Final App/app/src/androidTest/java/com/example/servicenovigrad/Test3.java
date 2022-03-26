package com.example.servicenovigrad;

import android.widget.Button;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class Test3 {
    //create one JUnit test here

    @Rule
    public ActivityTestRule<WelcomePageAdmin> mMainActivityTestRule = new ActivityTestRule(WelcomePageAdmin.class);

    public WelcomePageAdmin mWelcomePageAdmin = null;

    @Before
    public void setUp() throws Exception{
        mWelcomePageAdmin = mMainActivityTestRule.getActivity();
    }
    @Test
    public void checkimagebutton(){
        Button welcomePageAdminview = (Button) mWelcomePageAdmin.findViewById(R.id.logOutButton);
        assertNotNull(welcomePageAdminview);
    }
    @After
    public void tearDown() throws Exception{
        mWelcomePageAdmin = null;
    }

}