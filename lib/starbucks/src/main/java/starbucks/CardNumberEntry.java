package starbucks;

/**
 *Container for the Card Number input text box
 */
public class CardNumberEntry extends CardWidget {


    public CardNumberEntry(IKeyPadSubject kp) {
        super(9);

        this.kp = kp;
        kp.attach(cm);
    }

    /**
     * User Touch at X,Y Coordinates
     *
     * @param x X Coordinate
     * @param y Y Coordinate
     */
    public void touch(int x, int y) {

        if ((x > 0) && (x < 4) && (y == 2)) {
            kp.attach(cm);

        } else if (y <= 4) {
            kp.removeObserver(cm);
        }

        super.touch(x, y);
    }


    /**
     * Display handler
     * @return String to display
     */
    public String display() {

        String value = "";
        value += "  [";
        value += super.display();
        value += "]";
        return value;
    }

    /**
     * Get the cardNumber
     * @return the card number in string format
     */
    public String getCardNumber() {
        return super.display();
    }

    @Override
    public void cardDetailUpdate(String number, Double balance, Boolean newCard) {

    }
}
