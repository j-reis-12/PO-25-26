package bci.work.category;

/**
 * Class representing the Reference category.
 */
public class Reference extends Category {
    @Override
    public boolean isReference() { return true; }

    @Override
    public String label() { return "Referência"; }
}
