package bci.request.rules;

import bci.user.User;
import bci.work.Work;

public class NotUserSuspended extends RequestRule {
    @Override
    public int id() { return 2; }
    
    @Override
    public boolean ok(User user, Work work) { return !user.isSuspended(); }
}
