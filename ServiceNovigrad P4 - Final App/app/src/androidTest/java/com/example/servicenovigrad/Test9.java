package com.example.servicenovigrad;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class Test9 {
    @Rule
    public ActivityTestRule<searchForBranchByHours> mBranchHoursTestRule = new ActivityTestRule(searchForBranchByHours.class);

    public searchForBranchByHours mBranchHours = null;

    @Before
    public void setUp() throws Exception{
        mBranchHours = mBranchHoursTestRule.getActivity();
    }
    @Test
    public void checkView(){
        View search= mBranchHours.findViewById(R.id.searchView);
        View text= mBranchHours.findViewById(R.id.textView22);
        View list= mBranchHours.findViewById(R.id.listViewBranches);

        assertNotNull(search);
        assertNotNull(text);
        assertNotNull(list);
    }
    @After
    public void tearDown() throws Exception{
        mBranchHours = null;
    }
}
