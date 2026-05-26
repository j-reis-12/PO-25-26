package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.BorrowingRuleFailedException;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.request.rules.InvalidRequestException;
import bci.user.UserNotFoundException;
import bci.work.WorkNotFoundException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.4.1. Request work.
 */
class DoRequestWork extends Command<LibraryManager> {

    DoRequestWork(LibraryManager receiver) {
        super(Label.REQUEST_WORK, receiver);
        addIntegerField("userId", bci.app.user.Prompt.userId());
        addIntegerField("workId", bci.app.work.Prompt.workId());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            int dueDate = _receiver.requestWork(integerField("userId"), integerField("workId"));
            _display.popup(Message.workReturnDay(integerField("workId"), dueDate)); }
        catch (UserNotFoundException e) { throw new NoSuchUserException(integerField("userId")); }
        catch (WorkNotFoundException e) { throw new NoSuchWorkException(integerField("workId")); }
        catch (InvalidRequestException  e) {
            int ruleId = e.getRuleId();
            if (ruleId == 3) { // Ask the user for notification preference if work is not available
                String answer = Form.requestOption(Prompt.returnNotificationPreference(), new String[] {"s", "n"}).toLowerCase();
                
                if (answer.equals("s")) try { _receiver.setWorkReturnNotification(integerField("userId"), integerField("workId")); }
                catch (UserNotFoundException | WorkNotFoundException e1) { e1.printStackTrace(); }
            }
            else throw new BorrowingRuleFailedException(integerField("userId"), integerField("workId"), ruleId); }
    }

}
