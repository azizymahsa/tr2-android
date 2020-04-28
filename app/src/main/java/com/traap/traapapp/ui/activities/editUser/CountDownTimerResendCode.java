package com.traap.traapapp.ui.activities.editUser;

import android.os.CountDownTimer;

import com.traap.traapapp.ui.activities.login.LoginView;

import java.util.Calendar;

/**
 * Created by Mahsa.azizi
 */
public class CountDownTimerResendCode extends CountDownTimer
{
    private UserEditVerifyView loginView;

    public CountDownTimerResendCode(long millisInFuture, long countDownInterval, UserEditVerifyView loginView)
    {
        super(millisInFuture, countDownInterval);
        this.loginView = loginView;
    }

    @Override
    public void onTick(long l)
    {
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(l);

        int minutes = (int) (l / 60000);
        int second = time.get(Calendar.SECOND);
        String secondStr = "";
        if (second < 10)
        {
            secondStr = "0" + second;
        }
        else
        {
            secondStr = String.valueOf(second);
        }
        loginView.onTick(minutes + ":" + secondStr);

    }

    @Override
    public void onFinish()
    {
        loginView.onFinishTimer();

    }


}
