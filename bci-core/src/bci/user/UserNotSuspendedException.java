package bci.user;

public class UserNotSuspendedException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public UserNotSuspendedException(int userId) {
        super("Erro: o utente " + userId + " não está suspenso.");
    }
}
