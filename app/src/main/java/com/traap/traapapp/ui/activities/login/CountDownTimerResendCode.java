package com.traap.traapapp.ui.activities.login;

import android.os.CountDownTimer;

/**
 * Created by Javad.Abadi on 7/14/2018.
 */
public class CountDownTimerResendCode extends CountDownTimer {
    private LoginView loginView;

    public CountDownTimerResendCode(long millisInFuture, long countDownInterval, LoginView loginView ) {
        super(millisInFuture, countDownInterval);
        this.loginView=loginView;
    }

    @Override
    public void onTick(long l) {
        loginView.onTick((l / 60000) + ":"+ (l % 60000 / 1000));

    }

    @Override
    public void onFinish() {
        loginView.onFinishTimer();

    }


}
