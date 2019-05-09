

package starbucks;

/** My Card More Options Screen */
public class MyCardsMoreOptions extends Screen
{
  
    public MyCardsMoreOptions()
    {
    }


    /**
     * Handles the display for this class
     * @return String to display
     */
    public String display() {

        String value = "";
        value += "Refresh\n";
        value += "Reload\n";
        value += "Auto Reload\n";
        value += "Transactions";

        return value;
    }
}
