package bci.request.rules;

import bci.user.User;
import bci.work.Work;

public class NotPriceLimitExceeded extends RequestRule {
    public static final int PRICE_LIMIT = 25;

    @Override
    public int id() { return 6; }

    @Override
    public boolean ok(User user, Work work) {
        return user.getBehavior().isCompliant() ? true : work.getPrice() <= PRICE_LIMIT;
    }
    
}