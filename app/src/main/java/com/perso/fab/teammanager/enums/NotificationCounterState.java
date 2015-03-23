package com.perso.fab.teammanager.enums;

/**
 * Created by pcdev on 20/03/2015.
 */
public enum NotificationCounterState {
    /**
     * Used when the counter start from 0
     */
    START,
    /**
     * Used when the counter is stopped
     */
    PAUSE,
    /**
     * Used when the counter has been stopped and restart, not from 0
     */
    CONTINUE,
    /**
     * Used when the counter is reseted to 0
     */
    RESET
}
