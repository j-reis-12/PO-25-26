package bci.work.creator;

import java.util.Arrays;

public interface WorkCreators {
    public Creator[] getCreators();

    /**
     * Determine if the creator(s) match the search criteria.
     * 
     * @param search the search string
     * @return true if any creator's name contains the search string (case-insensitive), false otherwise.
     */
    default public boolean creatorMatches(String search) {
        Creator[] creators = this.getCreators();
        if (creators == null) return false;
        return Arrays.stream(creators)
                    .map(Object::toString)
                    .map(String::toLowerCase)
                    .anyMatch(name -> name.contains(search));
    }
}
