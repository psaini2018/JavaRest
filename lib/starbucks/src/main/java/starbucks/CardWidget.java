

package starbucks;

/**
 * Card Number/Code Screen Abstract class Component
 */
public abstract class CardWidget implements ITouchEventHandler, IDisplayComponent, ICardInputObserver {

    protected CardEntryMachine cm;
    protected IKeyPadSubject kp;

    private ITouchEventHandler nextHandler;
    private boolean inputCompleted;

    public CardWidget(int space) {

        inputCompleted = false;
        cm = new CardEntryMachine(space);
        ((ICardInputSubject) cm).registerObserver(this);
    }

    /**
     * Touch Event Ignored by CardInput. It is handled by parent
     *
     * @param x Touch X
     * @param y Touch Y
     */
    public void touch(int x, int y) {
        if (y <= 4) {
            System.err.println("Card Touched at (" + x + ", " + y + ")");
        }
        if (nextHandler != null)
            nextHandler.touch(x, y);
    }

    /**
     * Set Next Touch Handler
     *
     * @param next Touch Event Handler
     */
    public void setNext(ITouchEventHandler next) {
        nextHandler = next;
    }


    /**
     * Display "Echo Feedback" on Pins enterred so far
     *
     * @return Passcode String for Display
     */
    public String display() {
        return cm.display();
    }

    /**
     * Add Sub Component (Not used)
     *
     * @param c Sub Component to Add
     */
    public void addSubComponent(IDisplayComponent c) {
    }

    /**
     * Checks  if input in the txt box is completed
     * @return : True if all the inputs are completed.
     */
    public Boolean isInputCompleted() {
        return (null != nextHandler) ? (nextHandler.isInputCompleted() & inputCompleted) : inputCompleted;
    }

    /**
     * Clears the txt box if partial entry is entered
     * @param status status True or false. Here it is always clean
     */
    public void clearInputs(Boolean status) {

        cm.reinit();
        inputCompleted = status;

        if (nextHandler != null)
            nextHandler.clearInputs(status);
    }


    /**
     * Receive the State machine completion event
     * @param status
     */
    public void inputCompleteEvent(Boolean status) {
        inputCompleted = status;
    }
}
