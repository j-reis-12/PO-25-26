package bci.work;

public class InvalidUpdateAmountException  extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public InvalidUpdateAmountException() {
        super("Erro: quantidade a atualizar inválida.");
    }

}
