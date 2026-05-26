package bci.request.rules;

import bci.user.User;
import bci.work.Work;

public class NotWorkUnavailable extends RequestRule {
    @Override
    public int id() { return 3; }
    
    @Override
    public boolean ok(User user, Work work) { return work.isAvailable(); }
}
