

package starbucks;

/** Settings Screen */
public class Settings extends Screen
{

    private AddCard addCard;

    public Settings()
    {
        addCard = new AddCard();

        addCard.setPrev(this, "C") ;
    }


    /**
     * Get Display Contents
     * @return Display Contents
     */
    @Override
    public String display() {

        String value = "" ;
        value += "Add Card \n";
        value += "Delete Card \n";
        value += "Billing\n";
        value += "Passcode\n";
        value += "\n";
        value += "About|Terms\n";
        value += "Help";

        return value ;
    }

    /**
     * Touch handler
     * @param x Touch X Coord.
     * @param y Touch Y Coord.
     */
    public void touch(int x, int y) {

        if((x > 3) || (x < 1)){
            System.err.println( "Setting Touched at (" + x + ", " + y + ")" ) ;
            return;
        }

        if(y == 1)
        {
            frame.setCurrentScreen(addCard);
        }
    }

}


