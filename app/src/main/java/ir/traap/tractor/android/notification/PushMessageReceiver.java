package ir.traap.tractor.android.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.legacy.content.WakefulBroadcastReceiver;

import com.adpdigital.push.AdpPushClient;
import com.adpdigital.push.PushMessage;
import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.eventbus.EventBus;

import ir.traap.tractor.android.utilities.Logger;

/**
 * Created by Javad.Abadi on 9/9/2018.
 */
public class PushMessageReceiver extends WakefulBroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle messageData = intent.getExtras();
        String channel = messageData.getString(AdpPushClient.PUSH_MSG_RECEIVED_TOPIC);
        String body = messageData.getString(AdpPushClient.PUSH_MSG_RECEIVED_MSG);
        PushMessage push = PushMessage.fromJson(body, channel);
        handleNewMessage(push);
        completeWakefulIntent(intent);
        Logger.e("mmmmm", "Got push message " );

    }


    private void handleNewMessage(PushMessage message) {
        Prefs.putInt("notiCount", Prefs.getInt("notiCount",0)+1);
//        EventBus.getDefault().post(new NotificationDbModel());


    }

}
