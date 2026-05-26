package bci.request.rules;

import bci.user.User;
import bci.work.Work;

public class NotRequestLimitExceeded extends RequestRule {
    @Override
    public int id() { return 4; }
    
    @Override
    public boolean ok(User user, Work work) {
        return user.getCurrentRequests() < user.getRequestLimit();
    }
    
}
