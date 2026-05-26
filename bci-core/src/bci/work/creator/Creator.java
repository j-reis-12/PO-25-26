package bci.work.creator;

import java.util.List;

import bci.work.Work;
import bci.work.WorkWriter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that represents a creator of any kind of work.
 * Has a name (identifier) and a list of works made by this creator.
 */
public class Creator implements Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    private String _name;
    private List<Work> _works;
    private boolean _deleted = false;

    public Creator(String name) {
        _name = name;
        _works = new ArrayList<Work>();
    }

    public String getName() { return _name; }
    public List<Work> getWorks() { return _works; }

    public boolean isDeleted() { return _deleted; }

    public void setDeleted(boolean deleted) { _deleted = deleted; }

    public void addWork(Work work) { _works.add(work); }
    public void removeWork(Work work) { _works.remove(work); }
    public boolean hasWorks() { return !_works.isEmpty(); }

    @Override
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() { return this.getName(); }
}
