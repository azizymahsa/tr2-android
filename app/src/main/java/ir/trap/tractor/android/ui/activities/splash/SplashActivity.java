package ir.trap.tractor.android.ui.activities.splash;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.ui.activities.login.LoginActivity;
import ir.trap.tractor.android.ui.activities.main.MainActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity
{

    @Override
    protected void attachBaseContext(Context context)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startActivity(new Intent(SplashActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
}
