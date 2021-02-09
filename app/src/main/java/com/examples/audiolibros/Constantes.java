package com.examples.audiolibros;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Constantes {
    public interface ACTION {
        public static String MAIN_ACTION = "action.main";
        public static String PREV_ACTION = "action.prev";
        public static String PLAY_ACTION = "action.play";
        public static String PAUSE_ACTION = "action.pause";
        public static String NEXT_ACTION = "action.next";
        public static String CLOSE_ACTION = "action.close";
        public static String STARTFOREGROUND_ACTION = "action.startforeground";
        public static String STOPFOREGROUND_ACTION = "action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 1;
    }
}
