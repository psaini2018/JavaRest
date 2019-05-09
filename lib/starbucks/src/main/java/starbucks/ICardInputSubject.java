

package starbucks ;

/** Card input completion Subject */
public interface ICardInputSubject
{
    /**
     * Add a Card Input Observer
     * @param obj Observer Object
     */
    void registerObserver( ICardInputObserver obj ) ;

    /**
     * Remove Observer
     * @param obj Pin AUth Observer to Remove
     */
    void removeObserver( ICardInputObserver obj ) ;

    /**
     * Broadcast Event to Observers
     * @param state Current state to be notified. Authorised or not
     */
    void notifyObserver( Boolean state ) ;

}
