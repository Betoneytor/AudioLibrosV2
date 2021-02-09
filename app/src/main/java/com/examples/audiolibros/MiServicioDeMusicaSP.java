package com.examples.audiolibros;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;


public class MiServicioDeMusicaSP extends Service implements MediaPlayer.OnPreparedListener
{
    public MiServicioDeMusicaSP() {

    }

    private static final String TAG = "BackgroundSoundService";
    MediaPlayer player;

    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "onBind()" );
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();




    }
    public int onStartCommand(Intent intent, int flags, int startId) {

        //validamos que la accion sea reproducir

        if(intent.getAction().equals(Constantes.ACTION.PLAY_ACTION)) {

            String ULibro = intent.getStringExtra("urlLibro");

            player = new MediaPlayer();
            Uri audio = Uri.parse(ULibro);
            player.setOnPreparedListener(this);
            try {
                player.setDataSource(getApplicationContext(), audio);
                player.prepareAsync();
            } catch (IOException e) {
                Log.e("Audiolibros", "ERROR: No se puede reproducir " + audio, e);
            }

            player.setLooping(true); // Set looping
            player.setVolume(100, 100);


            //para android superior a 8 se necesita crear un canal
            String channelId = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channelId = createNotificationChannel("my_service", "My Background Service");
            }

            //Builder de la notificacion
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);

            //accion al darle click a la notificacion
            Intent in = new Intent(getApplicationContext(), MainActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, in, PendingIntent.FLAG_UPDATE_CURRENT);

            //Notificacion
            Notification myNotication;
            notificationBuilder.setAutoCancel(false);
            notificationBuilder.setTicker("Es un Ticket");
            notificationBuilder.setContentTitle("Audio Libro Reproduciendose");
            notificationBuilder.setContentText("Toque para volver a la aplicacion");
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
            notificationBuilder.setContentIntent(pendingIntent);
            notificationBuilder.setOngoing(true);
            notificationBuilder.setSubText("Subtexto...");   //API level 16
            notificationBuilder.setNumber(100);
            notificationBuilder.build();
            myNotication = notificationBuilder.build();

            //lanzar la notificacion
            startForeground(101, myNotication);

        }
        return Service.START_STICKY;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(String channelId, String channelName){
        NotificationChannel chan = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor( Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(chan);
    return channelId;
}


    @Override
    public void onPrepared(MediaPlayer mp) {
        player.start();
    }
    public IBinder onUnBind(Intent arg0) {
        Log.i(TAG, "onUnBind()");
        return null;
    }

    public void onStop() {
        Log.i(TAG, "onStop()");
    }
    public void onPause() {
        Log.i(TAG, "onPause()");
    }
    @Override
    public void onDestroy() {
        if (player!=null) {
            player.stop();
            player.release();
        }
        Toast.makeText(this, "Service stopped...", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onCreate() , service stopped...");
    }

    @Override
    public void onLowMemory() {
        Log.i(TAG, "onLowMemory()");
    }


}
