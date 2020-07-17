package de.joachimsohn.collectivity.util.logging;

import android.util.Log;

import de.joachimsohn.collectivity.ui.Marker;

public class Logger {

    public static void log(Priority priority, Marker marker, String msg) {
        Log.println(priority.getPriority(), marker.getText(), msg);
    }

}
