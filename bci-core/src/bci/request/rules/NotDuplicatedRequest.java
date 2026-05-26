package bci.request.rules;

import bci.user.User;
import bci.work.Work;

public class NotDuplicatedRequest extends RequestRule {
    @Override
    public int id() { return 1; }

    @Override
    public boolean ok(User user, Work work) {
        return user.getRequests().stream()
                    .noneMatch(request -> request.getWork().getId() == work.getId());
    }
}
