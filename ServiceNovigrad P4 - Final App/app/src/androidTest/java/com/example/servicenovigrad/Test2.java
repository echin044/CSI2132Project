package com.example.servicenovigrad;

import android.widget.Button;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class Test2 {
    //create one JUnit test here

    @Rule
    public ActivityTestRule<SignUp> mMainActivityTestRule = new ActivityTestRule(SignUp.class);

    public SignUp mSignUp = null;

    @Before
    public void setUp() throws Exception{
        mSignUp = mMainActivityTestRule.getActivity();
    }
    @Test
    public void checksignupbutton(){
        Button signupview = (Button) mSignUp.findViewById(R.id.btnSignUp);
        assertNotNull(signupview);
    }
    @After
    public void tearDown() throws Exception{
        mSignUp = null;
    }

}