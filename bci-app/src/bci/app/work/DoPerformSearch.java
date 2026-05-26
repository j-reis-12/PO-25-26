package bci.app.work;

import bci.LibraryManager;
import bci.work.WorkWriter;
import pt.tecnico.uilib.menus.Command;

/**
 * 4.3.5. Perform search according to miscellaneous criteria.
 */
class DoPerformSearch extends Command<LibraryManager> {

    DoPerformSearch(LibraryManager receiver) {
        super(Label.PERFORM_SEARCH, receiver);
        addStringField("search", Prompt.searchTerm());
    }

    @Override
    protected final void execute() {
        _receiver.searchWorks(stringField("search")).stream()
            .forEach(e -> _display.addLine(e));
    }

}
