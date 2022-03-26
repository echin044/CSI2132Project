package com.example.servicenovigrad;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class Test7 {
    @Rule
    public ActivityTestRule<Apply_User> mApplyTestRule = new ActivityTestRule(Apply_User.class);

    public Apply_User mApply = null;

    @Before
    public void setUp() throws Exception{
        mApply = mApplyTestRule.getActivity();
    }
    @Test
    public void checklistview(){
        View userview = mApply.findViewById(R.id.listViewServices);
        assertNotNull(userview);
    }
    @After
    public void tearDown() throws Exception{
        mApply = null;
    }
}
