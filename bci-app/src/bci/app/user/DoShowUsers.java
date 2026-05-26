package bci.app.user;

import bci.LibraryManager;
import bci.user.UserWriter;
import pt.tecnico.uilib.menus.Command;

/**
 * 4.2.4. Show all users.
 */
class DoShowUsers extends Command<LibraryManager> {

    DoShowUsers(LibraryManager receiver) {
        super(Label.SHOW_USERS, receiver);
    }

    @Override
    protected final void execute() {
        _receiver.selectUsers(_ -> true).stream()
            .forEach(e -> _display.addLine(e));
    }

}
