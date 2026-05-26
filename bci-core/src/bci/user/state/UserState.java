package bci.user.state;

import java.io.Serializable;

import bci.user.User;

/**
 * Abstract base class for different user states.
 */
public abstract class UserState implements Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    protected User _user; // for access to user data when switching states

    public UserState(User user) { _user = user; }
    
    public abstract String label();
    public abstract boolean isSuspended();
    public abstract void updateState(int date);
}
