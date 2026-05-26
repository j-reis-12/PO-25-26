package bci.work.creator;

public class MaxCreatorsException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public MaxCreatorsException(String filename) {
        super("Erro: Número máximo de criadores permitido para a obra ultrapassado.");
    }

}
