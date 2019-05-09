

package starbucks ;

/** Touch Event Handlers */
public interface ITouchEventHandler
{

    /**
     * Touch Event at X and Y
     * @param x X Coord
     * @param y Y Coord
     */
    void touch(int x, int y);

    /**
     * Set Next Handler in Event Chain
     * @param next Next Handler Object
     */
    void setNext( ITouchEventHandler next) ;

    /**
     * Check if input is completed
     * @return true if the inputs are completed
     */
    Boolean isInputCompleted();

    /**
     * Clear inputs for success or failure case
     * @param status true = inputs completed success
     * false = input completed false
     */
    void clearInputs(Boolean status);
}
