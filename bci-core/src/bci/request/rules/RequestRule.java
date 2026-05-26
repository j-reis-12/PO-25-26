package bci.request.rules;

import java.io.Serializable;

import bci.user.User;
import bci.work.Work;

public abstract class RequestRule implements Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    public static final int RULE_SUCCESS = -1;

    /**
     * Returns the unique identifier of the rule.
     * @return the rule ID
     */
    public abstract int id();

    /**
     * Checks if the rule is satisfied for the given user and work.
     * @param user the user making the request
     * @param work the work to be requested
     * @return true if the rule is satisfied, false otherwise
     */
    public abstract boolean ok(User user, Work work);

    /**
     * Returns the ID of the failed rule if not satisfied, or RULE_SUCCESS if none failed.
     * 
     * @param user the user making the request
     * @param work the work to be requested
     * @return RULE_SUCCESS if the rule is satisfied, otherwise the rule ID
    */
    public int failed(User user, Work work) { return ok(user, work) ? RULE_SUCCESS : id(); }
}
