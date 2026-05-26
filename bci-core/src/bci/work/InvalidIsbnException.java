package bci.work;

public class InvalidIsbnException extends Exception{

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public InvalidIsbnException(String name) {
        super("Erro: o isbn " + name + " é inválido.");
    }

}
