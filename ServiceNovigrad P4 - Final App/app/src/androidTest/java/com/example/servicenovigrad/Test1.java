package com.example.servicenovigrad;

import android.widget.Button;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class Test1 {
    //create one JUnit test here

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule = new ActivityTestRule(MainActivity.class);

    public MainActivity mMainActivity = null;

    @Before
    public void setUp() throws Exception{
        mMainActivity = mMainActivityTestRule.getActivity();
    }
    @Test
    public void checkmainbutton(){
        Button mainview = (Button) mMainActivity.findViewById(R.id.btnLogin);
        assertNotNull(mainview);
    }
    @After
    public void tearDown() throws Exception{
        mMainActivity = null;
    }

}
