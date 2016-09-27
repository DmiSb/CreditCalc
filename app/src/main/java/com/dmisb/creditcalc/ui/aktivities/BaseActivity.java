package com.dmisb.creditcalc.ui.aktivities;

import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

/**
 * BaseActivity
 */

public class BaseActivity extends AppCompatActivity {

    public void showSnackBar(CoordinatorLayout coordinatorLayout, String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }


    public void finishWithPause(int delayMillis) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, delayMillis);
    }

}
