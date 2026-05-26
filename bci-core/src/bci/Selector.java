package bci;

/**
 * Predicate interface for entity selection.
 * @param <Entity> type of the entity to test
 */
@FunctionalInterface
public interface Selector<Entity> {
	/**
	 * @param entity the entity
	 * @return true if selected, false otherwise.
	 */
	boolean ok(Entity entity);
}