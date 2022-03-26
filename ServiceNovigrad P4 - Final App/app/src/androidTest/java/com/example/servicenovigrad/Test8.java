package com.example.servicenovigrad;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class Test8 {
    @Rule
    public ActivityTestRule<ServiceFromSearch> mSearchTestRule = new ActivityTestRule(ServiceFromSearch.class);

    public ServiceFromSearch mSearch = null;

    @Before
    public void setUp() throws Exception{
        mSearch = mSearchTestRule.getActivity();
    }
    @Test
    public void checkimageview(){
        View userview1 = mSearch.findViewById(R.id.textView5);
        View userview2 = mSearch.findViewById(R.id.textView6);
        View userview3 = mSearch.findViewById(R.id.textView10);

        assertNotNull(userview1);
        assertNotNull(userview2);
        assertNotNull(userview3);

        View userlist = mSearch.findViewById(R.id.listViewServices);

        assertNotNull(userlist);
    }
    @After
    public void tearDown() throws Exception{
        mSearch = null;
    }
}
