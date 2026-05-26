package bci.work;

public class WorkNotFoundException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public WorkNotFoundException(int id) {
        super("Erro: a obra " + id + " não existe.");
    }
}
