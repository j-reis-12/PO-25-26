package bci.work.category;

import java.io.Serializable;

/**
 * Abstract base class for different work categories.
 */
public abstract class Category implements Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public abstract boolean isReference();

    public abstract String label();
}
