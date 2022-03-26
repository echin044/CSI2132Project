package com.example.servicenovigrad;

import android.widget.Button;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class Test6 {
    @Rule
    public ActivityTestRule<create_branch> mCreateBranchTestRule = new ActivityTestRule(create_branch.class);

    public create_branch mCreateBranch = null;

    @Before
    public void setUp() throws Exception{
        mCreateBranch = mCreateBranchTestRule.getActivity();
    }
    @Test
    public void checkbranchbutton(){
        Button userview = (Button) mCreateBranch.findViewById(R.id.btnCreateBranchC);
        assertNotNull(userview);
    }
    @After
    public void tearDown() throws Exception{
        mCreateBranch = null;
    }
}