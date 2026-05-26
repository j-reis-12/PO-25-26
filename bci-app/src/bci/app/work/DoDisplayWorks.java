package bci.app.work;

import bci.LibraryManager;
import bci.work.WorkWriter;
import pt.tecnico.uilib.menus.Command;

/**
 * 4.3.2. Display all works.
 */
class DoDisplayWorks extends Command<LibraryManager> {

    DoDisplayWorks(LibraryManager receiver) {
        super(Label.SHOW_WORKS, receiver);
    }

    @Override
    protected final void execute() {
        _receiver.selectWorks(_ -> true).stream()
            .forEach(e -> _display.addLine(e));
    }
}
