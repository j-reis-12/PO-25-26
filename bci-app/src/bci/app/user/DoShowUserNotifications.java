package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.user.UserNotFoundException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.3. Show notifications of a specific user.
 */
class DoShowUserNotifications extends Command<LibraryManager> {

    DoShowUserNotifications(LibraryManager receiver) {
        super(Label.SHOW_USER_NOTIFICATIONS, receiver);
        addIntegerField("id", Prompt.userId());
    }

    @Override
    protected final void execute() throws CommandException {
        try { _receiver.getUserNotifications(integerField("id")).stream()
            .forEach(e -> _display.addLine(e));
            _receiver.clearUserNotifications(integerField("id"));
        }
        catch (UserNotFoundException e) { throw new NoSuchUserException(integerField("id")); }
    }

}
