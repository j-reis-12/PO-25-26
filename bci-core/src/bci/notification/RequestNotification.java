package bci.notification;

import bci.work.Work;

public class RequestNotification extends Notification {
    public RequestNotification(Work work) { super(work); }
    
    @Override
    public String getMessage() { return "DISPONIBILIDADE: " + _work; }
}