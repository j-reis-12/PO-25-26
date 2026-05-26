package bci.user;

public class InvalidUserException extends Exception{

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public InvalidUserException(String name, String email) {
        super("Erro: o utente " + name + " com o email " + email + " não é válido.");
    }
}
