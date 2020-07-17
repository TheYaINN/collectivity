package de.joachimsohn.collectivity.util.logging;

import android.util.Log;

import lombok.Getter;

@Getter
public enum Priority {

    DEBUG(Log.DEBUG);

    private int priority;

    Priority(int priority) {
        this.priority = priority;
    }
}
