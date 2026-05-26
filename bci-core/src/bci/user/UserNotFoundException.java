package bci.user;

public class UserNotFoundException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public UserNotFoundException(int id) {
        super("Erro: o utente " + id + " não existe.");
    }

}
