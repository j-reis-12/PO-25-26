package bci.user;

/**
 * Printer class for User instances.
 * Implements the UserVisitor interface to provide a visit method for printing user details.
 */
public class UserWriter {
    public String write(User u) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format("%d - %s - %s - %s - %s",
            u.getId(),
            u.getName(),
            u.getEmail(),
            u.getBehavior().label(),
            u.getState().label()));

        if (u.isSuspended()) sb.append(String.format(" - EUR %d", u.getFine()));
        return sb.toString();
    }
}
