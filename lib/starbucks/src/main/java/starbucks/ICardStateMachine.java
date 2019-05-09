package starbucks;

/**
 * Card input state Machine
 */
public interface ICardStateMachine {

    /**
     * Return Display Component Contents
     * @return Display Component Contents
     */
    String display() ;

    /**
     * Handle the move to next state
     * @param next This state object
     */
    void moveNextState(ICardState next);

    /**
     * Handles the move the previous state
     * @param prev This Object
     */
    void movePrevState(ICardState prev);
}
