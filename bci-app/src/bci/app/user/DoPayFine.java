package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.UserIsActiveException;
import bci.user.UserNotFoundException;
import bci.user.UserNotSuspendedException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.5. Settle a fine.
 */
class DoPayFine extends Command<LibraryManager> {

    DoPayFine(LibraryManager receiver) {
        super(Label.PAY_FINE, receiver);
        addIntegerField("userId", Prompt.userId());
    }

    @Override
    protected final void execute() throws CommandException {
        try { _receiver.clearUserFine(integerField("userId")); }
        catch (UserNotFoundException e) { throw new NoSuchUserException(integerField("userId")); }
        catch (UserNotSuspendedException e) { throw new UserIsActiveException(integerField("userId")); }
    }

}
