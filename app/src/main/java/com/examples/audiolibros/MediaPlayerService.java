package com.examples.audiolibros;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.media2.MediaController;
import androidx.media2.MediaPlayer;
import androidx.media2.MediaSession;
import androidx.media2.MediaSessionManager;

public class MediaPlayerService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }
}