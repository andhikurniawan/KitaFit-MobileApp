package com.akasa.kitafit.adapter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.akasa.kitafit.R;
import com.akasa.kitafit.activity.Reminderku;
import com.akasa.kitafit.model.AppDatabase;
import com.akasa.kitafit.model.Reminders;
import com.akasa.kitafit.model.RoomDAO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class NotifAlarm extends BroadcastReceiver {
    private AppDatabase appDatabase;
    DatabaseReference mref;
    private FirebaseUser user;
    String UID;
    @Override
    public void onReceive(Context context, Intent intent) {
//        appDatabase = AppDatabase.geAppdatabase(context.getApplicationContext());
//        RoomDAO roomDAO = appDatabase.getRoomDAO();
//        Reminders reminder = new Reminders();
//        reminder.setMessage(intent.getStringExtra("Message"));
//        reminder.setRemindDate(new Date(intent.getStringExtra("RemindDate")));
//        reminder.setId(intent.getIntExtra("id",0));
//        roomDAO.Delete(reminder);
//        AppDatabase.destroyInstance();
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        UID=user.getUid();
//
//        DatabaseReference refku = FirebaseDatabase.getInstance().getReference("Reminder");
//        refku.child(UID).child("1").child("img").setValue("https://firebasestorage.googleapis.com/v0/b/kitafit-f4d8d.appspot.com/o/User%2Falarm.png?alt=media&token=fc7d6126-1892-4610-a3b2-e119dc367264");

        Uri alarmsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        Intent intent1 = new Intent(context, Reminderku.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(Reminderku.class);
        taskStackBuilder.addNextIntent(intent1);

        PendingIntent intent2 = taskStackBuilder.getPendingIntent(1,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("my_channel_01","hello", NotificationManager.IMPORTANCE_HIGH);
        }

        Notification notification = builder.setContentTitle("KitaFit")
                .setContentText(intent.getStringExtra("Message")).setAutoCancel(true)
                .setSound(alarmsound).setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(intent2)
                .setChannelId("my_channel_01")
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(1, notification);

    }
    }

