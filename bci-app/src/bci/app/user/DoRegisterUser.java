package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.UserRegistrationFailedException;
import bci.user.InvalidUserException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.1. Register new user.
 */
class DoRegisterUser extends Command<LibraryManager> {

    DoRegisterUser(LibraryManager receiver) {
        super(Label.REGISTER_USER, receiver);
        addStringField("name", Prompt.userName());
        addStringField("email", Prompt.userEMail());
    }

    @Override
    protected final void execute() throws CommandException {
        try { _display.popup(Message.registrationSuccessful(_receiver.registerUser(stringField("name"), stringField("email")))); }
        catch (InvalidUserException e) { throw new UserRegistrationFailedException(stringField("name"), stringField("email")); }
    }

}
