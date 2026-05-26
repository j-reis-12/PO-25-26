package bci.user.behavior;

import java.io.Serializable;

import bci.user.User;
import bci.work.Work;

/**
 * Abstract base class for different user behaviors.
 */
public abstract class UserBehavior implements Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public static final int WORK_INVENTORY_REQUEST_LIMIT = 5;

    protected User _user; // for access to user data when switching states
    private int _requestLimit;

    public UserBehavior(User user, int requestLimit) { 
        _user = user;
        _requestLimit = requestLimit;
    }

    public int getRequestLimit() { return _requestLimit; }

    public abstract int getLoanPeriodSingleCopy();
    public abstract int getLoanPeriodBelowEqualLimitCopies();
    public abstract int getLoanPeriodAboveLimitCopies();

    public int getLoanPeriod(Work work) {
        int copies = work.getTotalCopies();
        if (copies == 1) return getLoanPeriodSingleCopy();
        if (copies <= WORK_INVENTORY_REQUEST_LIMIT) return getLoanPeriodBelowEqualLimitCopies();
        return getLoanPeriodAboveLimitCopies();
    }

    public abstract boolean isCompliant();

    public abstract void updateBehavior();
    
    public abstract String label();
}
