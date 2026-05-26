package bci.user.behavior;

import bci.user.User;

public class AbsentBehavior extends UserBehavior {

    public AbsentBehavior(User user) { super(user, 1); }

    @Override
    public int getLoanPeriodSingleCopy() { return 2; }

    @Override
    public int getLoanPeriodBelowEqualLimitCopies() { return 2; }

    @Override
    public int getLoanPeriodAboveLimitCopies() { return 2; }

    @Override
    public boolean isCompliant() { return false; }

    @Override
    public void updateBehavior() {
        if (_user.getConsecutiveSuccesses() >= 3) _user.setBehavior(new NormalBehavior(_user));
    }

    @Override
    public String label() { return "FALTOSO"; }
    
}