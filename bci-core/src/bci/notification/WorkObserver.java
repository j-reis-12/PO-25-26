package bci.notification;

public interface WorkObserver {
    public static enum ObserverType { REQUEST, AVAILABILITY }

    /**
     * Add a notification for the entity observing the work.
     * 
     * @param n the Notification to be added
     */
    void addNotification(Notification n);
    ObserverType getObserverType();
}
