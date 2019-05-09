

package starbucks;

import java.text.DecimalFormat;

/**
 * My Cards Screen
 */
public class MyCards extends Screen {
    private Cards card;
    private MyCardsPay myCardsPay;
    private MyCardsOptions myCardsOptions;

    public MyCards() {
        card = Cards.getInstance();

        myCardsPay = new MyCardsPay();
        myCardsOptions = new MyCardsOptions();
    }

    /**
     * Handles display in pretty format
     * @return String to print
     */
    public String display() {
        return card.display();
    }

    /**
     * Handles the touch for my card screen
     * @param x Touch X Coord.
     * @param y Touch Y Coord.
     */
    public void touch(int x, int y) {

        if ((x == 3) && (y == 3)) {
            frame.setCurrentScreen(myCardsPay);

        } else if ((x == 2) && (y == 4)) {
            frame.setCurrentScreen(myCardsOptions);
        }
    }

}
