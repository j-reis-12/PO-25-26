package bci.work;

import java.util.Arrays;
import java.util.stream.Collectors;

import bci.work.creator.Creator;

/**
 * Abstract base class for Work printers.
 * Provides functionality for printing common Work details.
 */
public class WorkWriter{
    /**
     * Returns the formatted common fields for all kinds of Work.
     * 
     * @param work the Work whose details are to be formatted
     * @return the formatted string
     */
    public String formatCommonFields(Work w) {
        return String.format("%d - %d de %d - %s - %s - %d - %s",
            w.getId(),
            w.getAvailableCopies(),
            w.getTotalCopies(),
            w.getType(),
            w.getTitle(),
            w.getPrice(),
            w.getCategory().label());
    }

    /**
     * Formats an array of creators into a single string.
     * Creators are separated by ";".
     * 
     * @param creators the array of creators to format
     * @return the string representation of the creator(s)
     */
    public String formatCreators(Creator[] creators) {
        return Arrays.stream(creators)
                    .map(Object::toString)
                    .collect(Collectors.joining("; "));
    
    }

    /**
     * Formats a Book into a string representation.
     * 
     * @param book the Book to format
     * @return the string representation of the Book
     */
    public String write(Book book) {
        return  String.format("%s - %s - %s", formatCommonFields(book),  formatCreators(book.getCreators()), book.getIsbn());
    }

    /**
     * Formats a DVD into a string representation.
     * 
     * @param dvd the DVD to format
     * @return the string representation of the DVD
     */
    public String write(Dvd dvd) {
        return String.format("%s - %s - %s", formatCommonFields(dvd), formatCreators(dvd.getCreators()), dvd.getIgac());
    }

}
