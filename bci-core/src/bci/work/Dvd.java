package bci.work;

import bci.work.category.Category;
import bci.work.creator.Creator;
import bci.work.creator.WorkCreators;

/**
 * Class that represents a DVD work.
 * Has an IGAC code as additional information.
 */
public class Dvd extends Work implements WorkCreators {
    private Creator _director;
    private String _igac;

    public Dvd(int id, String title, Creator creator, int price, Category category, int copies, String igac) {
        super(id, title, price, category, copies);
        _director = creator;
        _igac = igac;
    }

    public Creator[] getCreators() { return new Creator[] { _director }; }

    public String getIgac() { return _igac; }

    @Override
    public String getType() { return "DVD"; }

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
