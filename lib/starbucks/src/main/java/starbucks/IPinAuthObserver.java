

package starbucks ;

/** Pin Auth Observer Interface */
public interface IPinAuthObserver
{
    /**
     * Auth Event
     * @param status Input success or failure
     */
    void authEvent(Boolean status) ;
}
