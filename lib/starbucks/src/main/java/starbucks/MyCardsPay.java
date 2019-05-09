

package starbucks;

/** My Card Pay Screen */
public class MyCardsPay extends Screen
{
    private  Cards card;

    public MyCardsPay() {

        card = Cards.getInstance();

    }

    /**
     * Handles the display for this class
     * @return String to display
     */
    public String display() {
        String value = "[";
        value += card.getCardNumber();
        value += "]";
        value += "\n";
        value += "\n";
        value += "\n";
        value += "Scan Now";

        return value;
    }

    /**
     * Handles touch in this screen
     * @param x Touch X Coord.
     * @param y Touch Y Coord.
     */
    public void touch(int x, int y) {

        if((x == 3) && (y == 3)){
            frame.selectA();
        }
        else if((x == 2) && (y == 2)){
            card.subtractCardBalance(card.getCardNumber(), 1.50);
        }
    }

}

