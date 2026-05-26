package bci.user.state;

import bci.user.User;

public class SuspendedState extends UserState {

    public SuspendedState(User user) { super(user); }

    @Override
    public String label() { return "SUSPENSO"; }

    @Override
    public boolean isSuspended() { return true; }

    @Override
    public void updateState(int date) {
        if (_user.getRequests().stream().allMatch(r -> r.getDueDate() >= date)
        && _user.getFine() == 0)
            _user.setState(new ActiveState(_user));
    }
    
}
