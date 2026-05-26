package bci.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import bci.user.behavior.*;
import bci.user.behavior.UserBehavior;
import bci.user.state.*;
import bci.request.Request;
import bci.notification.Notification;
import bci.notification.WorkObserver;

/**
 * Class representing a User in the system.
 */
public class User implements Serializable, Comparable<User>, WorkObserver {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public static final int FINE_MULTIPLIER = 5; // fine per overdue day
    
    private int _id;
    private String _name;
    private String _email;
    private UserBehavior _behavior = new NormalBehavior(this);
    private UserState _state = new ActiveState(this);
    private int _fine = 0;
    private List<Request> _requests = new ArrayList<Request>();
    private int _consecutiveFailures = 0;
    private int _consecutiveSuccesses = 0;
    private List<Notification> _notifications = new ArrayList<Notification>();
    private ObserverType _observerType;

    public User(int id, String name, String email) {
        _id = id;
        _name = name;
        _email = email;
    }

    public int getId() { return _id; }
    public String getName() { return _name; }
    public String getEmail() { return _email; }
    public UserBehavior getBehavior() { return _behavior; }
    public UserState getState() { return _state; }
    public int getFine() { return _fine; }
    public List<Request> getRequests() { return _requests; }
    public int getConsecutiveFailures() { return _consecutiveFailures; }
    public int getConsecutiveSuccesses() { return _consecutiveSuccesses; }
    public ObserverType getObserverType() { return _observerType; }

    public boolean isSuspended() { return _state.isSuspended(); }
    public void updateState(int date) { _state.updateState(date); }

    public int getCurrentRequests() { return _requests.size(); }
    public int getRequestLimit() { return _behavior.getRequestLimit(); }

    public void setBehavior(UserBehavior behavior) { _behavior = behavior; }
    public void setState(UserState state) { _state = state; }
    public void setObserverType(ObserverType type) { _observerType = type; }

    public String accept(UserWriter writer) { return writer.write(this); }

    public int addFine(int n) { return _fine += (FINE_MULTIPLIER * n); }
    public void payFine(int n) { _fine -= n; }
    public void clearFine() { _fine = 0; }

    public void clearNotifications() { _notifications.clear(); }

    @Override
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() { return this.accept(new UserWriter()); }

    @Override
    /**
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(User other) { 
        // compare by name first, ID if equal names
        int nameComparison = this._name.compareToIgnoreCase(other._name);
        if (nameComparison != 0) return nameComparison;
        return Integer.compare(this._id, other._id);
    }

    public void registerFailure() {
        _consecutiveFailures++;
        _consecutiveSuccesses = 0;
        _behavior.updateBehavior();
    }

    public void registerSuccess() {
        _consecutiveSuccesses++;
        _consecutiveFailures = 0;
        _behavior.updateBehavior();
    }

    @Override
    /**
     * @see bci.notification.WorkObserver#update()
     */
    public void addNotification(Notification n) { _notifications.add(n); }

    public List<Notification> getNotifications() { return _notifications; }
}
