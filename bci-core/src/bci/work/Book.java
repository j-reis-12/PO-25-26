package bci.work;

import bci.work.category.Category;
import bci.work.creator.Creator;
import bci.work.creator.WorkCreators;

/**
 * Class that represents a Book work.
 * Has an ISBN as additional information.
 */
public class Book extends Work implements WorkCreators {
    private Creator[] _authors;
    private String _isbn;

    public Book(int id, String title, Creator[] creators, int price, Category category, int copies, String isbn) {
        super(id, title, price, category, copies);
        _authors = creators;
        _isbn = isbn;
    }

    public Creator[] getCreators() { return _authors; }

    public String getIsbn() { return _isbn; }

    @Override
    public String getType() { return "Livro"; }

    @Override
    /**
     * @see bci.work.Work#accept(bci.work.WorkWriter)
     */
    public String accept(WorkWriter writer) { return writer.write(this); }

    @Override
    /**
     * @see bci.work.Work#matches(String)
     */
    public boolean matches(String search) { return creatorMatches(search) || titleMatches(search); }

}
