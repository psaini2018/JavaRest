

package starbucks;

import java.util.ArrayList;

/**
 * Card Entry Machine - Context for State Pattern
 */
public class CardEntryMachine implements ICardStateMachine, IKeyPadObserver, ICardInputSubject {
    // current state
    private ICardState state;
    private IStateIterator iterator;

    private ICardInputObserver inputObserver; // single observer

    /**
     * Get name of current state for unit testing
     *
     * @return Class Name of Current State
     */
    public String getCurrentState() {
        return " Current State" + iterator.getCursor();
    }


    /**
     * Constructor - Set Up State Objects
     * and Set Initial State to "PIN 0"
     */
    public CardEntryMachine(int stateCount) {

        ArrayList<ICardState> cardStates = new ArrayList<ICardState>();

        for (int i = 0; i < stateCount; i++) {
            CardState s = new CardState(this);
            cardStates.add(s);
        }

        this.iterator = new StateIterator(cardStates);
        this.state = iterator.init();
    }

    /**
     * Display handler. Handled via a iterator
     * @return String to print
     */
    public String display() {
        return iterator.display();
    }


    /**
     * Reinit to put the state machine  to the initial state
     */
    public void reinit(){
        this.state = iterator.init();
        System.out.println(" Resetting SEM " + getCurrentState());
    }

    /**
     * Handles the next State Movement
     * @param state Current State object
     */
    public void moveNextState(ICardState state) {
        this.state = iterator.next();

        if(iterator.isDone()){
            notifyObserver(true);
        }
    }

    /**
     * Handles the previous state movement.
     * @param state Current State object
     */
    public void movePrevState(ICardState state) {
        if(iterator.isDone()) {
            notifyObserver(false);
        }
        this.state = iterator.prev();
    }

    /**
     * Backspace Event
     */
    public void backspace() {
        this.state.backspace();
    }


    /**
     * Number Event
     *
     * @param digit Digit Pressed
     */
    public void number(String digit) {
        if(!iterator.isDone()) {
            this.state.number(digit);
        }
    }

    /**
     * Observer of Key Events
     *
     * @param c   Num Keys So Far
     * @param key Last Key Enterred
     */
    public void keyEventUpdate(int c, String key) {
        System.err.println("Key: " + key + " Count: " + c);
        if (key.equals(" "))
            /* nothing */ ;
        else if (key.equals("X"))
            backspace();
        else
            number(key);
    }

    /**
     * Register Observers for Pin Authentication
     *
     * @param obj Object Observing Pin Auth
     */
    public void registerObserver(ICardInputObserver obj) {
        this.inputObserver = obj;
    }

    /**
     * Remove Pin Auth Observer
     *
     * @param obj Object No Longer Observing Pin Auth
     */
    public void removeObserver(ICardInputObserver obj) {
        this.inputObserver = null;
    }


    /**
     * Notify Pin Auth Observers
     * @param state State to be notified the observer
     */
    public void notifyObserver(Boolean state) {
        if (this.inputObserver == null)
            return;

        this.inputObserver.inputCompleteEvent(state);
    }

}
