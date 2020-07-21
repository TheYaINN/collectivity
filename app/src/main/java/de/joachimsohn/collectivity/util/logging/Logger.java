package de.joachimsohn.collectivity.util.logging;

import android.util.Log;

import lombok.Getter;

public class Logger {

    public static void log(Priority priority, Marker marker, String msg) {
        Log.println(priority.getPriority(), marker.getText(), msg);
    }

    @Getter
    public enum Priority {

        DEBUG(Log.DEBUG);

        private int priority;

        Priority(int priority) {
            this.priority = priority;
        }
    }

    public enum Marker {

        MAIN(">> Main: "),
        DB(">> Database: "),
        STORAGELOCATION(">> StorageLocation: "),
        SEARCH(">> Search:"),
        CACHEMANAGER(">> CacheManager: ");

        @Getter
        private String text;

        Marker(String text) {
            this.text = text;
        }

    }
}
