package com.example.servicenovigrad;

import android.widget.Button;
import android.widget.Switch;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class Test11 {
    @Rule
    public ActivityTestRule<services_update_dialog> mServiceDlgTestRule = new ActivityTestRule(services_update_dialog.class);

    public services_update_dialog mServiceDlg = null;

    @Before
    public void setUp() throws Exception{
        mServiceDlg = mServiceDlgTestRule.getActivity();
    }
    @Test
    public void checkView() throws Throwable{
        Button pressEdit = (Button) mServiceDlg.findViewById(R.id.btnEditService);
        Button pressDelete = (Button) mServiceDlg.findViewById(R.id.btnDeleteService);
        Switch pressSwitch =  (Switch) mServiceDlg.findViewById(R.id.switchExpiration);

        assertNotNull(pressEdit);
        assertNotNull(pressDelete);
        assertNotNull(pressSwitch);

        assertFalse(pressEdit.performClick());
        assertFalse(pressDelete.performClick());
        assertFalse(pressSwitch.callOnClick());

    }

    @After
    public void tearDown() throws Exception{
        mServiceDlg = null;
    }
}
