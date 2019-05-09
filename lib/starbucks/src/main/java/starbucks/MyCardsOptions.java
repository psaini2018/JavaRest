

package starbucks;

/** My Card Options Screen */
public class MyCardsOptions extends Screen
{
    private MyCardsMoreOptions myCardsMoreOptions;
   
    public MyCardsOptions() {

        myCardsMoreOptions = new MyCardsMoreOptions();
       
    }

    /**
     * Handles the touch in this screen
     * @param x Touch X Coord.
     * @param y Touch Y Coord.
     */
    public void touch(int x, int y) {

        if ((x >0 ) && (x <= 3) && (y == 7)) {
            frame.setCurrentScreen(myCardsMoreOptions);
        }
    }

    /**
     * Handles the display
     * @return String to print
     */
    public String display() {

        String value = "";
        value += "Reload\n";
        value += "Refresh\n";
        value += "More Options\n";
        value += "Cancel";

        return value;
    }

}
