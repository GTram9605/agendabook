package za.ac.nplinnovations.agendabook.notifcations;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationHelperChannel2 notificationHelper2 = new NotificationHelperChannel2(context);
        NotificationCompat.Builder nb2 = notificationHelper2.getChannelNotification2();
        notificationHelper2.getManager2().notify(2, nb2.build());
    }
}
