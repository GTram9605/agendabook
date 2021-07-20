package za.ac.nplinnovations.agendabook.notifcations;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import za.ac.nplinnovations.agendabook.R;

public class NotificationHelperChannel2 extends ContextWrapper {
    public static final String channe2ID = "channe2ID";
    public static final String channe2Name = "Channe2 Name";

    private NotificationManager mManager;

    public NotificationHelperChannel2(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channe2ID, channe2Name, NotificationManager.IMPORTANCE_HIGH);

        getManager2().createNotificationChannel(channel);
    }

    public NotificationManager getManager2() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification2() {
        return new NotificationCompat.Builder(getApplicationContext(), channe2ID)
                .setContentTitle("Appointment Reminder")
                .setContentText("You have an appointment tomorrow")
                .setSmallIcon(R.drawable.ic_baseline_calendar_today_24)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setVibrate(new long[]{1000, 1000})
                .setLights(Color.RED, 3000, 3000)
                .setAutoCancel(true);

    }

    Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
            R.drawable.ic_baseline_calendar_today_24);

}
