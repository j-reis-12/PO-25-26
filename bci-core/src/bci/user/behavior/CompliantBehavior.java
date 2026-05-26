package bci.user.behavior;

import bci.user.User;

public class CompliantBehavior extends UserBehavior {

    public CompliantBehavior(User user) { super(user, 5); }

    @Override
    public int getLoanPeriodSingleCopy() { return 8; }

    @Override
    public int getLoanPeriodBelowEqualLimitCopies() { return 15; }

    @Override
    public int getLoanPeriodAboveLimitCopies() { return 30; }

    @Override
    public boolean isCompliant() { return true; }

    @Override
    public void updateBehavior() {
        if (_user.getConsecutiveFailures() >= 1) _user.setBehavior(new NormalBehavior(_user));
    }

    @Override
    public String label() { return "CUMPRIDOR"; }
    
}
