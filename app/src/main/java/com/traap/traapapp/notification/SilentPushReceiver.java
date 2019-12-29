package com.traap.traapapp.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.traap.traapapp.utilities.Logger;
import com.yandex.metrica.push.YandexMetricaPush;

public class SilentPushReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // Extract push message payload from your push message.
        String payload = intent.getStringExtra(YandexMetricaPush.EXTRA_PAYLOAD);
        Logger.e("--Yandex payload--", "payload: " + payload);

        String installationSource = "";
        if (intent.hasExtra("referrer"))
        {
            installationSource = intent.getStringExtra("referrer");
            Logger.e("--Yandex referrer--", String.format("Referrer received: %s", installationSource));
        }
//        ...
    }
}
