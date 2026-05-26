package bci.request;

public class NotBorrowedWorkException extends Exception {
    
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public NotBorrowedWorkException(int workId, int userId) {
        super("A obra " + workId + " não foi requisitada pelo utente " + userId + ".");
    }
}
