package bci.user.behavior;

import bci.user.User;

public class NormalBehavior extends UserBehavior {

    public NormalBehavior(User user) { super(user, 3); }

    @Override
    public int getLoanPeriodSingleCopy() { return 3; }

    @Override
    public int getLoanPeriodBelowEqualLimitCopies() { return 8; }

    @Override
    public int getLoanPeriodAboveLimitCopies() { return 15; }

    @Override
    public boolean isCompliant() { return false; }

    @Override
    public void updateBehavior() {
        if (_user.getConsecutiveFailures() >= 3) _user.setBehavior(new AbsentBehavior(_user));
        else if (_user.getConsecutiveSuccesses() >= 5) _user.setBehavior(new CompliantBehavior(_user));
    }

    @Override
    public String label() { return "NORMAL"; }
}