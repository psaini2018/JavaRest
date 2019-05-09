package starbucks;


/**
 *Container for the Code input text box
 */
public class CardCodeEntry extends CardWidget {

    public CardCodeEntry(IKeyPadSubject kp) {

        super(3);
        this.kp = kp;
    }

    /**
     * Handles the display for the Card code text box
     * @return String to display the output
     */
    public String display() {

        String value = "";
        value += "     [";
        value += super.display();
        value += "]";
        return value;
    }

    /**
     * User Touch at X,Y Coordinates
     *
     * @param x X Coordinate
     * @param y Y Coordinate
     */
    public void touch(int x, int y) {

        if ((x == 2) && (y == 3)) {
            kp.attach(cm);

        } else if (y <= 4) {
            kp.removeObserver(cm);
        }
        super.touch(x, y);
    }

    @Override
    public void cardDetailUpdate(String number, Double balance, Boolean newCard) {

    }
}
