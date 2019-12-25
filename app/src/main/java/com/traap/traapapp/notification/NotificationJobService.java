package com.traap.traapapp.notification;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;

import androidx.annotation.RequiresApi;

//import com.adpdigital.push.AdpPushClient;
//import com.adpdigital.push.PushMessage;
import com.pixplicity.easyprefs.library.Prefs;

import com.traap.traapapp.utilities.Logger;


/**
 * Created by Javad.Abadi on 9/12/2018.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NotificationJobService extends JobService
{


    @Override
    public void onCreate()
    {
        super.onCreate();

        if (!Prefs.getString("mobile", "").isEmpty() || !Prefs.getString("mobile", "").equals(null))
        {
//            AdpPushClient client = AdpPushClient.get().register(Prefs.getString("mobile", ""));
//            client.addListener(this);
        }
    }

//    public void onEvent(PushMessage message)
//    {
//        Logger.d("123", "Got push message " + message);
//        Prefs.putInt("notiCount", Prefs.getInt("notiCount", 0) + 1);
////        EventBus.getDefault().post(new NotificationDbModel());
//
//    }

    @Override
    public boolean onStartJob(JobParameters jobParameters)
    {

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
