package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.work.WorkNotFoundException;
import bci.work.InvalidUpdateAmountException;

/**
 * 4.3.4. Change the number of exemplars of a work.
 */
class DoChangeWorkInventory extends Command<LibraryManager> {

    DoChangeWorkInventory(LibraryManager receiver) {
        super(Label.CHANGE_WORK_INVENTORY, receiver);
        addIntegerField("id", Prompt.workId());
        addIntegerField("amount", Prompt.amountToUpdate());
    }

    @Override
    protected final void execute() throws CommandException {

        try { _receiver.updateWorkInventory(integerField("id"), integerField("amount"));}
        catch (WorkNotFoundException e) {
            _display.popup(Message.noSuchWork(integerField("id")));
        }
        catch (InvalidUpdateAmountException e) {
            _display.popup(Message.notEnoughInventory(integerField("id"), integerField("amount")));
        }
    }

}
