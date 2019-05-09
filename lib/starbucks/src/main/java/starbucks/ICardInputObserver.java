

package starbucks ;

/** Card Input completion Observer Interface */
public interface ICardInputObserver
{
    /**
     * Card input completion Event
     * @param status Input success or failure
     */
    void inputCompleteEvent(Boolean status) ;

    void cardDetailUpdate(String number, Double balance, Boolean newCard) ;
}
