package com.example.servicenovigrad;

import android.view.View;
import android.widget.Button;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class Test12 {
    @Rule
    public ActivityTestRule<rating_system> mRatingTestRule = new ActivityTestRule(rating_system.class);

    public rating_system mRating = null;

    @Before
    public void setUp() throws Exception{
        mRating = mRatingTestRule.getActivity();
    }
    @Test
    public void checkbutton(){

        View pressbar = mRating.findViewById(R.id.ratingBar);
        Button pressrate = (Button) mRating.findViewById(R.id.btnRate);
        Button pressback = (Button) mRating.findViewById(R.id.btnBack);

        assertNotNull(pressbar);
        assertNotNull(pressrate);
        assertNotNull(pressback);

        View txtview = mRating.findViewById(R.id.textView13);
        assertNotNull(txtview);

    }
    @After
    public void tearDown() throws Exception{
        mRating = null;
    }
}
