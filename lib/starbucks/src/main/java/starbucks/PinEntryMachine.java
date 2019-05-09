

package starbucks;

/**
 * Pin Entry Machine - Context for State Pattern
 */
public class PinEntryMachine implements IPinStateMachine, IKeyPadObserver, IPinAuthSubject {

    /**
     * Get name of current state for unit testing
     *
     * @return Class Name of Current State
     */
    public String getCurrentState() {
        return state.getClass().getName();
    }

    // Pin Domain Object
    private IPinAuthObserver auth; // single observer

    // pin machine states
    private NoPinDigits pin0;
    private OnePinDigit pin1;
    private TwoPinDigits pin2;
    private ThreePinDigits pin3;
    private FourPinDigits pin4;

    // current state
    private IPinState state;

    // pin captured so far
    private String d1, d2, d3, d4;

    public String d1() {
        return d1;
    }

    public String d2() {
        return d2;
    }

    public String d3() {
        return d3;
    }

    public String d4() {
        return d4;
    }

    /**
     * Constructor - Set Up State Objects
     * and Set Initial State to "PIN 0"
     */
    public PinEntryMachine() {
        pin0 = new NoPinDigits(this);
        pin1 = new OnePinDigit(this);
        pin2 = new TwoPinDigits(this);
        pin3 = new ThreePinDigits(this);
        pin4 = new FourPinDigits(this);
        this.d1 = "";
        this.d2 = "";
        this.d3 = "";
        this.d4 = "";
        this.state = pin0;

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
        this.state.number(digit);
    }

    /**
     * Valid Pin Event
     */
    public void validPin() {
        this.state.validPin();
    }

    /**
     * Invalid Pin Event
     */
    public void invalidPin() {
        this.state.invalidPin();
    }

    /**
     * Change the State to No Pin State
     */
    public void setStateNoPinDigits() {
        this.state = pin0;
        this.d1 = "";
        this.d2 = "";
        this.d3 = "";
        this.d4 = "";
        debug();
    }

    /**
     * Change the State to One Pin State
     *
     * @param digit Digit/Number Enterred
     */
    public void setStateOnePinDigit(String digit) {
        this.state = pin1;
        if (digit != null)
            this.d1 = digit;
        else {
            this.d2 = "";
            this.d3 = "";
            this.d4 = "";
        }
        debug();
    }

    /**
     * Change the State to Two Pin State
     *
     * @param digit Digit/Number Enterred
     */
    public void setStateTwoPinDigits(String digit) {
        this.state = pin2;
        if (digit != null)
            this.d2 = digit;
        else {
            this.d3 = "";
            this.d4 = "";
        }
        debug();
    }

    /**
     * Change the State to Three Pin State
     *
     * @param digit Digit/Number Enterred
     */
    public void setStateThreePinDigits(String digit) {
        this.state = pin3;
        if (digit != null)
            this.d3 = digit;
        else {
            this.d4 = "";
        }
        debug();
    }

    /**
     * Change the State to Four Pin State
     *
     * @param digit Digit/Number Enterred
     */
    public void setStateFourPinDigits(String digit) {
        this.state = pin4;
        if (digit != null) {
            this.d4 = digit;
            debug();
            System.err.println("Authenticating...with pin" +
                    UserInfo.getInstance().GetPin(UserInfo.getInstance().GetUserName()));

            if (UserInfo.getInstance().GetPin(UserInfo.getInstance().GetUserName()).equals(d1 + d2 + d3 + d4)) {
                System.err.println("Successful Login!");
                validPin();
                notifyObserver(true);
            } else {
                System.err.println("Login Failed!");
                invalidPin();
                notifyObserver(false);
            }
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
    public void registerObserver(IPinAuthObserver obj) {
        this.auth = obj;
    }

    /**
     * Remove Pin Auth Observer
     *
     * @param obj Object No Longer Observing Pin Auth
     */
    public void removeObserver(IPinAuthObserver obj) {
        this.auth = null;
    }

    /**
     * Notify Pin Auth Observers
     *
     * @param state State to notify to the observer
     */
    public void notifyObserver(Boolean state) {
        if (this.auth != null)
            this.auth.authEvent(state);
    }

    /**
     * Debug Dump to Stderr State Machine Changes
     */
    private void debug() {
        System.err.println("Current State: " + state.getClass().getName());
        System.err.println("D1 = " + d1);
        System.err.println("D2 = " + d2);
        System.err.println("D3 = " + d3);
        System.err.println("D4 = " + d4);
    }

}
