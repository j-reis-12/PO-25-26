package bci.request.rules;

import bci.user.User;
import bci.work.Work;

public class NotReferenceWork extends RequestRule {
    @Override
    public int id() { return 5; }
    
    @Override
    public boolean ok(User user, Work work) {
        return !work.getCategory().isReference();
    }
    
}
