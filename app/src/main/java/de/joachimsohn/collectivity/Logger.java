package de.joachimsohn.collectivity;

import android.util.Log;

import de.joachimsohn.collectivity.ui.Marker;

public class Logger {

    public static void log(int priority, Marker marker, String msg) {
        Log.println(priority, marker.getText(), msg);
    }

}
