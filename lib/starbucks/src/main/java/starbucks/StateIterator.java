package starbucks;

import java.util.ArrayList;

/**
 * State Iterator package
 */
public class StateIterator implements IStateIterator {

    private ArrayList<ICardState> cs;
    private int max;
    private int cursor;

    public StateIterator(ArrayList<ICardState> cs) {

        this.cs = cs;
        max = cs.size();
        cursor = 0;
    }

    /**
     * Returns first state
     * @return first state
     */
    public ICardState first() {
        return cs.get(0);
    }

    /**
     * Returns Last state
     * @return last state
     */
    public ICardState last() {
        return cs.get(max - 1);
    }

    /**
     * Init handler.
     * @return Card state of the first state
     */
    public ICardState init() {
        cursor = 0;
        return current();
    }

    /**
     * Advances to next state
     * @return next state
     */
    public ICardState next() {
        if (!isDone()) {
            cursor++;
        }
        return isDone() ? last() : current();
    }

    /**
     * Move back to previous state
     * @return prev state
     */
    public ICardState prev() {
        if (cursor > 0) {
            cursor--;
        }
        return current();
    }

    /**
     * Checks if the reaches to the end of iterator
     * @return True if it is already reached end
     */
    public Boolean isDone() {
        return (cursor == max);
    }

    /**
     * Returns current state object
     * @return current object
     */
    public ICardState current() {
        return cs.get(cursor);
    }

    /**
     * Returns the current cursor
     * @return current cursor
     */
    public int getCursor() {
        return cursor;
    }


    /**
     * Handles the display of stored objects
     * @return value in string
     */
    public String display() {

        System.err.println("cursor" + cursor);

        String value = "";
        for (int i = 0; i < cursor; i++) {
            value += cs.get(i).display();
        }
        return value;
    }
}
