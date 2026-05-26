package bci.notification;

import bci.work.Work;

public class AvailabilityNotification extends Notification {
    public AvailabilityNotification(Work work) { super(work); }

    @Override
    public String getMessage() { return "DISPONIBILIDADE: " + _work; }
}
