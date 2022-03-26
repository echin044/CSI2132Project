package com.example.servicenovigrad;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class Test4 {
    //create one JUnit test here

    @Rule
    public ActivityTestRule<WelcomePageEmployee> mEmployeeTestRule = new ActivityTestRule(WelcomePageEmployee.class);

    public WelcomePageEmployee mEmployee = null;

    @Before
    public void setUp() throws Exception{
        mEmployee = mEmployeeTestRule.getActivity();
    }
    @Test
    public void checkimageview(){
        View employeeview = mEmployee.findViewById(R.id.welcomeText);
        assertNotNull(employeeview);
    }
    @After
    public void tearDown() throws Exception{
        mEmployee = null;
    }

}