package bci.work.category;

public class UnknownCategoryException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public UnknownCategoryException(String name) {
        super("Erro: a categoria " + name + " não existe.");
    }

}
