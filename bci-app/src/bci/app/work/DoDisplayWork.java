package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchWorkException;
import bci.work.WorkNotFoundException;
import bci.work.WorkWriter;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.3.1. Display work.
 */
class DoDisplayWork extends Command<LibraryManager> {

    DoDisplayWork(LibraryManager receiver) {
        super(Label.SHOW_WORK, receiver);
        addIntegerField("id", Prompt.workId());
    }

    @Override
    protected final void execute() throws CommandException {
        try { _display.addLine(_receiver.getWork(integerField("id"))); }
        catch (WorkNotFoundException e) { throw new NoSuchWorkException(integerField("id")); }
    }

}
