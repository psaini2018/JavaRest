package starbucks;

/**
 * Package for Card input State Machine
 */
public class CardState implements ICardState {

    String digit;
    CardEntryMachine machine;

    public CardState(CardEntryMachine machine){
        this.digit = "";
        this.machine = machine;
    }

    /**
     * Display handler
     * @return String to display
     */
    public String display() {
        return digit;
    }

    /**
     * Handles  Backspace key press
     */
    public void backspace() {

        this.digit = "";
        this.machine.movePrevState(this);
    }

    /**
     * Handle the numbers press
     * @param digit Digit Pressed
     */
    public void number(String digit) {

        this.digit = digit;
        this.machine.moveNextState(this);
    }

}
