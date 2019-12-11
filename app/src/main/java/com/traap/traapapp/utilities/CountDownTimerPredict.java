package com.traap.traapapp.utilities;

import android.os.CountDownTimer;

import com.traap.traapapp.ui.activities.login.LoginView;
import com.traap.traapapp.ui.fragments.main.CountDownTimerView;

import java.util.Calendar;

/**
 * Created by Javad.Abadi on 7/14/2018.
 */
public class CountDownTimerPredict extends CountDownTimer
{
    private CountDownTimerView actionView;
    private int day, hour, minutes, second;

    public CountDownTimerPredict(long millisInFuture, long countDownInterval, CountDownTimerView actionView)
    {
        super(millisInFuture, countDownInterval);
        this.actionView = actionView;
    }

    @Override
    public void onTick(long l)
    {
        String timeStr = "";
        if (l < 1000)
        {
            timeStr = "00 : 00 : 00 : 00";
        }
        else if (l >= 1000 && l < 60000) //second
        {
            Calendar time = Calendar.getInstance();
            time.setTimeInMillis(l);

            second = time.get(Calendar.SECOND);

            timeStr = "00 : 00 : 00:" + formatNum(second);
        }
        else if (l >= 60000 && l < 3600000) //minutes
        {
            Calendar time = Calendar.getInstance();
            time.setTimeInMillis(l);

            minutes = (int) (l / 60000);
            second = time.get(Calendar.SECOND);

            timeStr =  "00 : 00 : " + formatNum(minutes) + " : " + formatNum(second);
        }
        else if (l >= 3600000 && l < 86400000) //hour
        {
            Calendar time = Calendar.getInstance();
            time.setTimeInMillis(l);

            hour = (int) (l / 3600000);
            minutes = (int) ((l - hour * 3600000) / 60000);
            second = time.get(Calendar.SECOND);

            timeStr =  "00 : " + formatNum(hour) + " : " + formatNum(minutes) + " : " + formatNum(second);
        }
        else
        {
            Calendar time = Calendar.getInstance();
            time.setTimeInMillis(l);

            day = (int) (l / 86400000);
            hour = (int) ((l - day * 86400000) / 3600000);
            minutes = (int) ((l - day * 86400000 - hour * 3600000) / 60000);
            second = time.get(Calendar.SECOND);

            timeStr = formatNum(day) + " : " + formatNum(hour) + " : " + formatNum(minutes) + " : " + formatNum(second);
        }
        actionView.onTickTimer(timeStr);
    }

    @Override
    public void onFinish()
    {
        actionView.onFinishTimer();

    }

    private String formatNum(int time)
    {
        return time < 10 ? "0" + time : String.valueOf(time);
    }

}
