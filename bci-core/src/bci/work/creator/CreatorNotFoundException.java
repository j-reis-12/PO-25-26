package bci.work.creator;

public class CreatorNotFoundException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public CreatorNotFoundException(String id) {
        super("Erro: o criador " + id + " não existe.");
    }
}
