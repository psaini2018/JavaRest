/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddCardTest implements ICardInputObserver
{

    IApp app ;

    public AddCardTest()
    {
        Cards.getInstance().registerObserver(this);
    }

    @Before
    public void setUp()
    {
        app = new AppAuthProxy() ;  
    }

    @Test
    public void AddCardTest1()
    {
        String[] lines ;
        assertEquals("PinScreen", app.screen());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen());
        app.touch(1,1) ; // Add New Card
        assertEquals("AddCard", app.screen());
        // Card Id digits
        app.touch(1,5); // 1
        app.touch(2,5); // 2
        app.touch(3,5); // 3
        app.touch(1,6); // 4
        app.touch(2,6); // 5
        app.touch(3,6); // 6
        app.touch(1,7); // 7
        app.touch(2,7); // 8
        app.touch(3,7); // 9
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n"); 
        assertEquals("[123456789]", lines[4].trim());
        assertEquals("[999]", lines[5].trim());
        // add card - see balance
        app.next() ;    
        app.display() ;
        assertEquals("MyCards", app.screen());
        lines = app.screenContents().split("\n");  
        //assertEquals("$20.00", lines[7]);
        assertTrue("Failed", lines[7].contains("$20.00"));
    }
    

    @Test
    public void AddCardTest2()
    {
        String[] lines ;
        assertEquals("PinScreen", app.screen());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen());
        app.touch(1,1) ; // Add New Card
        assertEquals("AddCard", app.screen());
        // Card Id digits
        app.touch(1,5); // 1
        app.touch(2,6); // 5
        app.touch(3,7); // 9
        app.touch(3,6); // 6
        app.touch(2,6); // 5
        app.touch(2,5); // 2
        app.touch(1,7); // 7
        app.touch(1,6); // 4
        app.touch(1,6); // 4
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(1,7); // 7
        app.touch(2,6); // 5
        app.touch(3,5); // 3
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n");  
        assertEquals("[159652744]", lines[4].trim());
        assertEquals("[753]", lines[5].trim());
    }

    @Test
    public void AddCardTest3()
    {
        String[] lines ;
        assertEquals("PinScreen", app.screen());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen());
        app.touch(1,1) ; // Add New Card
        assertEquals("AddCard", app.screen());
        // Card Id digits
        app.touch(1,5); // 1
        app.touch(2,5); // 2
        app.touch(3,5); // 3
        app.touch(1,6); // 4
        app.touch(2,6); // 5
        app.touch(3,6); // 6
        app.touch(1,7); // 7
        app.touch(2,7); // 8
        app.touch(3,5); // 9
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[123456783]", lines[4].trim());
        assertEquals("[999]", lines[5].trim());
        // add card - see balance
        app.next() ;
        app.display() ;
        assertEquals("MyCards", app.screen());
        lines = app.screenContents().split("\n");
        //assertEquals("$20.00", lines[7]);
        assertTrue("Failed", lines[7].contains("$20.00"));
         app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen());
        app.touch(1,1) ; // Add New Card
        assertEquals("AddCard", app.screen());
        // Card Id digits
        app.touch(1,2); // focus on card number to enter rest of card no
        app.touch(1,5); // 1
        app.touch(2,5); // 2
        app.touch(3,5); // 3
        app.touch(1,6); // 4
        app.touch(2,6); // 5
        app.touch(3,6); // 6
        app.touch(1,7); // 7
        app.touch(2,7); // 8
        app.touch(3,7); // 9
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[123456789]", lines[4].trim());
        assertEquals("[999]", lines[5].trim());
        // add card - see balance
        app.next() ;
        app.display() ;
        assertEquals("MyCards", app.screen());
        lines = app.screenContents().split("\n");
        //assertEquals("$20.00", lines[7]);
        assertTrue("Failed", lines[7].contains("$20.00"));
    }

    @After
    public void tearDown()
    {
    }

    @Override
    public void inputCompleteEvent(Boolean status) {

    }

    @Override
    public void cardDetailUpdate(String number, Double balance, Boolean newCard) {

        System.out.println(number + "  "+ balance + " " + newCard);

    }
}
