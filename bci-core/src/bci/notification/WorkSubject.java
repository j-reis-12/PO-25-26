package bci.notification;

public interface WorkSubject {
    /** 
     * Register an observer to be notified of work updates.
     * 
     * @param o the WorkObserver to register
     */
    void registerObserver(WorkObserver o);

    /** 
     * Remove an observer from the notification list.
     * 
     * @param o the WorkObserver to remove
     */
    void removeObserver(WorkObserver o);

    /**
     * Notify all registered observers of an update.
     * @param n the Notification type to be sent to observers
     */
    void notifyObservers(Notification n);
}
