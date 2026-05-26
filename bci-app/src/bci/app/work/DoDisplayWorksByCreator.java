package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchCreatorException;
import bci.work.creator.CreatorNotFoundException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.3.3. Display all works by a specific creator.
 */
class DoDisplayWorksByCreator extends Command<LibraryManager> {

    DoDisplayWorksByCreator(LibraryManager receiver) {
        super(Label.SHOW_WORKS_BY_CREATOR, receiver);
        addStringField("id", Prompt.creatorId());
    }

    @Override
    protected final void execute() throws CommandException {
        try { _receiver.selectWorksByCreator(stringField("id"))
            .forEach(e -> _display.addLine(e)); }
        catch (CreatorNotFoundException e) { throw new NoSuchCreatorException(stringField("id")); }
    }

}
