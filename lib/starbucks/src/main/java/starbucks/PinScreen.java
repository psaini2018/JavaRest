

package starbucks ;

/** Pin Screen */
public class PinScreen extends Screen {

    private Boolean pinInvalid = false;
   
    public PinScreen()
    {
    }

    /**
     * Handles the display
     * @return string to print
     */
    public String display()
    {
        String value = "";
        if(pinInvalid) {
            value += "  Invalid Pin\n\n";
        }
        else {
            value += "\n\n";
        }
        return value + super.display() ;
    }

    /**
     * Touch handler for pin screen
     * @param x Touch X Coord.
     * @param y Touch Y Coord.
     */
    public void touch(int x, int y){
        this.pinInvalid = false; // Press any key to clear it
        super.touch(x,y);
    }

    /**
     * Clear the screen inputs handler
     * @param status if the previous inputs are success or not
     */
    @Override
    public void clearInputs(Boolean status) {
        this.pinInvalid = (status) ? false : true;
    }
}
