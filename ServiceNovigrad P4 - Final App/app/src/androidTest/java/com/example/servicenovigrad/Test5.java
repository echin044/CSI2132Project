package com.example.servicenovigrad;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class Test5 {
    //create one JUnit test here

    @Rule
    public ActivityTestRule<WelcomePageUsers> mUserTestRule = new ActivityTestRule(WelcomePageUsers.class);

    public WelcomePageUsers mUser = null;

    @Before
    public void setUp() throws Exception{
        mUser = mUserTestRule.getActivity();
    }
    @Test
    public void checkimageview(){
        View userview = mUser.findViewById(R.id.imageView3);
        assertNotNull(userview);
    }
    @After
    public void tearDown() throws Exception{
        mUser = null;
    }

}