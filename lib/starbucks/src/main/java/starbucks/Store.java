

package starbucks ;

/** Store Screen */
public class Store extends Screen
{

    public Store() {

    }

    /**
     * Handles the display
     * @return String to print
     */
    public String display() {

        String value = "";
        value += "      x \n";
        value += "   x   x \n";
        value += " x  x  x   x \n";
        value += "   x   x \n";
        value += "      x \n";

        return value;
    }

}
