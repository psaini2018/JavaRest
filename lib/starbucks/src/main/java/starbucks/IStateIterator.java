package starbucks;

/**
 * State Iterator Handler
 */
public interface IStateIterator {

    /**
     * Init handler.
     * @return Card state of the first state
     */
    ICardState init();

    /**
     * Returns first state
     * @return first state
     */
    ICardState first();

    /**
     * Returns Last state
     * @return last state
     */
    ICardState last();

    /**
     * Advances to next state
     * @return next state
     */
    ICardState next();

    /**
     * Move back to previous state
     * @return prev state
     */
    ICardState prev();

    /**
     * Checks if the reaches to the end of iterator
     * @return True if it is already reached end
     */
    Boolean isDone();

    /**
     * Returns current state object
     * @return current object
     */
    ICardState current();

    /**
     * Returns the current cursor
     * @return current cursor
     */
    int getCursor();

    /**
     * Handles the display of stored objects
     * @return value in string
     */
    String display();
}
