package starbucks;

/**
 * Interface to handle Card Input State Machine
 */
public interface ICardState {

     /**
     * Return Display Component Contents
     * @return Display Component Contents
     */
    String display() ;

    /** Backspace Event */
    void backspace() ;

    /**
     * Number Event
     * @param digit Digit Pressed
     */
    void number( String digit ) ;

}
