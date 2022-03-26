package com.example.servicenovigrad;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class Test10 {
    @Rule
    public ActivityTestRule<Notifcation> mNotifTestRule = new ActivityTestRule(Notifcation.class);

    public Notifcation mNotif = null;

    @Before
    public void setUp() throws Exception{
        mNotif= mNotifTestRule.getActivity();
    }
    @Test
    public void checkimageview(){
        View userview1 = mNotif.findViewById(R.id.textView7);
        View userview2 = mNotif.findViewById(R.id.textView8);
        View userview3 = mNotif.findViewById(R.id.textView11);

        assertNotNull(userview1);
        assertNotNull(userview2);
        assertNotNull(userview3);
    }
    @After
    public void tearDown() throws Exception{
        mNotif = null;
    }
}
