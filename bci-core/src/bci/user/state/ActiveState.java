package bci.user.state;

import bci.user.User;

/**
 * Class representing the Active state of a user.
 */
public class ActiveState extends UserState {

    public ActiveState(User user) { super(user); }

    @Override
    public String label() { return "ACTIVO"; }

    @Override
    public boolean isSuspended() { return false; }

    @Override
    public void updateState(int date) {
        if (_user.getRequests().stream().anyMatch(r -> r.getDueDate() < date)
        || _user.getFine() > 0)
            _user.setState(new SuspendedState(_user));
    }
}