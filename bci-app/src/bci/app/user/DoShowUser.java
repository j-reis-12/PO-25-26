package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.user.UserNotFoundException;
import bci.user.UserWriter;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.2. Show specific user.
 */
class DoShowUser extends Command<LibraryManager> {

    DoShowUser(LibraryManager receiver) {
        super(Label.SHOW_USER, receiver);
        addIntegerField("id", Prompt.userId());
    }

    @Override
    protected final void execute() throws CommandException {
        try { _display.addLine(_receiver.getUser(integerField("id"))); }
        catch (UserNotFoundException e) { throw new NoSuchUserException(integerField("id")); }
    }

}
