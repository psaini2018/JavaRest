
package starbucks;

/**
 * Add New Card Screen
 */
public class AddCard extends Screen {

    private KeyPad kp;
    private CardCodeEntry cc;
    private CardNumberEntry cn;
    private CardScreen cs;
    private Spacer sp;


    public AddCard() {

        kp = new KeyPad();
        cc = new CardCodeEntry(kp);
        cn = new CardNumberEntry(kp);
        sp = new Spacer();
        cs = new CardScreen();

        // setup the composite pattern
        cs.addSubComponent(cn);
        cs.addSubComponent(cc);
        cs.addSubComponent(sp);
        cs.addSubComponent(kp);
    }

    /**
     * User Touch at X,Y Coordinates
     *
     * @param x X Coordinate
     * @param y Y Coordinate
     */
    public void touch(int x, int y) {

        cs.touch(x, y);
    }

    /**
     * Display Screen Contents to Terminal
     * @return display information from the chain
     */
    public String display() {
        return "\n" + cs.display();
    }

    /**
     * Get Class Name of Screen
     *
     * @return Class Name of Current Screen
     */
    public String screen() {
        return cs.name();
    }


    /**
     * Handles the next button to add the card
     * Adds the card if all the inputs are good.
     * If not, clears the existing inputs
     */
    public void next() {

        if (cs.isInputCompleted()) {
            Cards.getInstance().setCardNumber(cn.getCardNumber());
            Cards.getInstance().setCardBalance(cn.getCardNumber(), 20.00);
            frame.selectA();
            cs.clearInputs(false);
        }
        else {
            cs.clearInputs(false);
        }
    }

    /**
     * Handling the previous menu
     */
    public void prev(){
        cs.clearInputs(false);
        super.prev();
    }

}
