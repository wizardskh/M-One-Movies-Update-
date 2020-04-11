package com.khhs.clientapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class NotiService extends FirebaseMessagingService {
    static int notiId=1;
    public NotiService() {
    }
    String title;
    String body;
    String imageUrl;





    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
       if(remoteMessage.getData().size()!=0)
       {

           title = remoteMessage.getData().get("title");
           body = remoteMessage.getData().get("body");

               imageUrl = remoteMessage.getData().get("image");


          new sendNoti().execute(title,body,imageUrl);
       }
       if(remoteMessage.getNotification()!=null)
       {
           title = remoteMessage.getNotification().getTitle();
           body = remoteMessage.getNotification().getBody();

               imageUrl = remoteMessage.getNotification().getImageUrl().toString();


           new sendNoti().execute(title,body,imageUrl);
       }
    }


    private class sendNoti extends AsyncTask<String,Void, Bitmap>
    {


        @Override
        protected Bitmap doInBackground(String... strings) {

            title = strings[0];
            body = strings[1];
            imageUrl = strings[2];

            try {
                URL imagehttpurl = new URL(imageUrl);
                HttpURLConnection connection =(HttpURLConnection) imagehttpurl.openConnection();
                connection.setDoInput(true);
                InputStream inputStream = connection.getInputStream();
                Bitmap myImage = BitmapFactory.decodeStream(inputStream);
                return myImage;
            }
            catch (Exception ex)
            {
                return  null;
            }

        }


        @Override
        protected void onPostExecute(Bitmap image) {
           Intent intent = new Intent(getApplicationContext(),MainActivity.class);
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent   = PendingIntent.getActivity(
                    getApplicationContext(),0,intent,PendingIntent.FLAG_ONE_SHOT);
            NotificationManager manager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                String channelid=getString(R.string.app_name);
                String channelName= getString(R.string.app_name);
                int importance = NotificationManager.IMPORTANCE_HIGH;

                NotificationChannel channel = new NotificationChannel(channelid,channelName,importance);

                manager.createNotificationChannel(channel);

                NotificationCompat.Builder noti = new NotificationCompat.Builder(getApplicationContext(),channelid);
                noti.setContentTitle(title)
                        .setContentText(body)
                        .setSmallIcon(R.drawable.ic_noti)
                        .setAutoCancel(true)
                        .setLargeIcon(image)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image))
                        .setColor(Color.MAGENTA)
                        .setContentIntent(pendingIntent)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
                manager.notify(notiId++,noti.build());
            }
            else
            {
                NotificationCompat.Builder noti = new NotificationCompat.Builder(getApplicationContext());
                noti.setContentTitle(title)
                        .setContentText(body)
                        .setSmallIcon(R.drawable.ic_noti)
                        .setAutoCancel(true)
                        .setLargeIcon(image)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image))
                        .setColor(Color.MAGENTA)
                        .setContentIntent(pendingIntent)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
                manager.notify(notiId++,noti.build());
            }
        }
    }


}
