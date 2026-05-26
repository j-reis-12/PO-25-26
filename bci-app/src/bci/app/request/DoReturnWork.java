package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.WorkNotBorrowedByUserException;
import bci.request.NotBorrowedWorkException;
import bci.user.UserNotFoundException;
import bci.work.WorkNotFoundException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.4.2. Return a work.
 */
class DoReturnWork extends Command<LibraryManager> {

    DoReturnWork(LibraryManager receiver) {
        super(Label.RETURN_WORK, receiver);
        addIntegerField("userId", bci.app.user.Prompt.userId());
        addIntegerField("workId", bci.app.work.Prompt.workId());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            int fine = _receiver.returnWork(integerField("userId"), integerField("workId"));
            if (fine > 0) {
                _display.popup(Message.showFine(integerField("userId"), fine));

                String answer = Form.requestOption(Prompt.finePaymentChoice(), new String[] {"s", "n"}).toLowerCase();
                if (answer.equals("s")) _receiver.payUserFine(integerField("userId"), fine);
            }
            _receiver.updateUserState(integerField("userId"));
        }
        catch (UserNotFoundException e1) { throw new NoSuchUserException(integerField("userId")); }
        catch (WorkNotFoundException e2) { throw new NoSuchWorkException(integerField("workId")); }
        catch (NotBorrowedWorkException e) {
            throw new WorkNotBorrowedByUserException(integerField("workId"), integerField("userId"));
        }
    }

}
