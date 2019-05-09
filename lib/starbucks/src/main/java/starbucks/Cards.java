package starbucks;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton card storage
 */
public class Cards implements ICardInputSubject {
    private static Cards card;

    private ICardInputObserver inputObserver; // single observer
    private static String INIT_CARD_NO = "000000000";

    // This has to be moved to list of cards.
    private Map<String, Double> cardInfo;
    private String defaultCard;

    private Cards() {
        defaultCard = INIT_CARD_NO;
        cardInfo = new HashMap<>();
        this.inputObserver = null; // single observer
    }

    public void reInit() {
        defaultCard = INIT_CARD_NO;
        cardInfo = new HashMap<>();
    }


    /**
     * Static Constructor
     *
     * @return this object.
     */
    public static Cards getInstance() {
        if (card == null) {
            card = new Cards();
        }
        return card;
    }

    /**
     * Get the Card number
     *
     * @return string of card number
     */
    public String getCardNumber() {
        return defaultCard;
    }

    /**
     * Set the card number
     *
     * @param number Input string
     */
    public void setCardNumber(String number) {
        if (defaultCard.equals(INIT_CARD_NO)) {
            defaultCard = number;
        }
        if (cardInfo.containsKey(number)) {
            notifyCardTriggers(number, false);
        }
        else {
            cardInfo.put(number, 0.0);
            notifyCardTriggers(number, true);
        }
    }

    /**
     * Get the card balance for the given card
     *
     * @param number Card number
     * @return Balance in $
     */
    public double getCardBalance(String number) {
        if (number.equals(INIT_CARD_NO)) {
            return 0.0;
        }
        return cardInfo.get(number);
    }

    /**
     * Set Card balance for the given card
     *
     * @param number  Card number
     * @param balance Amount
     */
    public void setCardBalance(String number, double balance) {
        if (cardInfo.containsKey(number)) {
            cardInfo.put(number, balance);
            notifyCardTriggers(number, false);
        }else {
            cardInfo.put(number, balance);
            notifyCardTriggers(number, true);
        }
    }

    /**
     * Subtract the card balance for the given card number
     *
     * @param number Card number
     * @param amount Amount to subtract
     */
    public Boolean subtractCardBalance(String number, double amount) {
        if (cardInfo.containsKey(number)) {
            if ((cardInfo.get(number) - amount) >= 0.0) {
                cardInfo.put(number, (cardInfo.get(number) - amount));
                notifyCardTriggers(number, false);
                return true;
            }
        }
        return false;
    }


    @Override
    public void registerObserver(ICardInputObserver obj) {
        this.inputObserver = obj;
    }

    @Override
    public void removeObserver(ICardInputObserver obj) {
        this.inputObserver = null;
    }

    @Override
    public void notifyObserver(Boolean state) {

    }

    private void notifyCardTriggers(String number, Boolean addFlag) {

        if(null != inputObserver) {
            System.out.println(" Notifying new card "
                    + number
                    + " "
                    + cardInfo.get(number)
                    + " "
                    + addFlag);
            inputObserver.cardDetailUpdate(number, cardInfo.get(number), addFlag);
        }
    }

    public String display() {
        String value = "";
        DecimalFormat fmt = new DecimalFormat("0.00");

        if(cardInfo.size() == 0){
            value += " $";
            value += fmt.format(0);
            value += "\n";
        }

        for (String number: cardInfo.keySet()){
            value += number;
            value += " $";
            value += fmt.format(cardInfo.get(number));
            value += "\n";
        }
        return value;
    }
}
