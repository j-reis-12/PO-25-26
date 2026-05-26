package bci.request.rules;

import bci.user.User;
import bci.work.Work;

import java.util.List;

public class AndRule extends RequestRule {
    private final List<RequestRule> _rules;

    public AndRule(RequestRule... rules) { _rules = List.of(rules); }

    @Override
    public int id() { return 0; } // id unnefective for composite rule

    @Override
    public boolean ok(User user, Work work) {
        return _rules.stream().allMatch(r -> r.ok(user, work));
    }

    @Override
    public int failed(User user, Work work) {
        for (RequestRule rule : _rules) {
            int failedId = rule.failed(user, work);
            if (failedId != RULE_SUCCESS) return failedId;
        }
        return RULE_SUCCESS;
    }
}