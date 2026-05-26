package bci.work.category;

/**
 * Class representing the Science and Technical category.
 */
public class SciTech extends Category {
    @Override
    public boolean isReference() { return false; }

    @Override
    public String label() { return "Técnica e Científica"; }
}
