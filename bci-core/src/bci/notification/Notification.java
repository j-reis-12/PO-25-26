package bci.notification;

import bci.work.Work;

public abstract class Notification {
    protected Work _work; // for access to work data to use in the message

    public abstract String getMessage();

    public Notification(Work work) { _work = work; }

    @Override
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() { return getMessage(); }
}
