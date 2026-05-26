package bci.work.category;

/**
 * Class representing the Fiction category.
 */
public class Fiction extends Category {
    @Override
    public boolean isReference() { return false; }

    @Override
    public String label() { return "Ficção"; }
}
