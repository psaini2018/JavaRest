/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UnitTest
{

    IApp app ;

    public UnitTest()
    {
    }

    @Before
    public void setUp()
    {
        app = new AppAuthProxy() ;  
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
        app.touch(2,6); // 5
        app.touch(3,7); // 9
        app.touch(3,6); // 6
        app.touch(2,6); // 5
        app.touch(2,5); // 2
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(1,7); // 7
        app.touch(2,6); // 5
        app.touch(3,5); // 3
        // Card Number digits
        app.touch(1,2); // focus on card number to enter rest of card no
        app.touch(1,6); // 4
        app.touch(1,7); // 7
        app.touch(1,6); // 4
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[159652474]", lines[4].trim());
        assertEquals("[753]", lines[5].trim());
    }

    @Test
    public void AddCardTest4()
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
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(1,7); // 7
        app.touch(2,6); // 5
        app.touch(3,5); // 3
        // Card Number digits
        app.touch(1,2); // focus on card number to enter rest of card no
        app.touch(1,6); // 4
        app.touch(1,1); // out of focus
        app.touch(1,6); // 4
        app.touch(1,2); // focus back on card number to enter rest of card no
        app.touch(1,7); // 7
        app.touch(1,6); // 4
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[159652474]", lines[4].trim());
        app.touch(3,8); // backspace
        app.touch(3,7); // 9 refill new number!!
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[159652479]", lines[4].trim());
        assertEquals("[753]", lines[5].trim());
    }

    @Test
    public void AddCardTest5()
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
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(1,7); // 7
        app.touch(2,6); // 5
        app.touch(3,5); // 3
        // Card Number digits
        app.touch(1,2); // focus on card number to enter rest of card no
        app.touch(1,6); // 4
        app.touch(1,1); // out of focus
        app.touch(1,6); // 4
        app.touch(1,2); // focus back on card number to enter rest of card no
        app.touch(1,7); // 7
        app.touch(1,6); // 4
        app.touch(3,7); // 9 Add Extra number!!
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[159652474]", lines[4].trim());
        assertEquals("[753]", lines[5].trim());
    }

    @Test
    public void AddCardTest6()
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
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(1,7); // 7
        app.touch(2,6); // 5
        app.touch(3,5); // 3
        // Card Number digits
        app.touch(1,2); // focus on card number to enter rest of card no
        app.touch(1,6); // 4
        app.touch(1,1); // out of focus
        app.touch(1,6); // 4
        app.touch(1,2); // focus back on card number to enter rest of card no
        app.touch(1,8); // space must be no action
        app.touch(1,7); // 7
        app.touch(1,6); // 4
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[159652474]", lines[4].trim());
        assertEquals("[753]", lines[5].trim());
        app.touch(1,8); // space must be no action
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[159652474]", lines[4].trim());
        assertEquals("[753]", lines[5].trim());
    }


     @Test
    public void AddCardTest7()
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
        app.touch(3,8); // backspace as first entry
        app.touch(1,5); // 1
        app.touch(3,8); // backspace
        app.touch(1,5); // 1
        app.touch(2,6); // 5
        app.touch(3,7); // 9
        app.touch(3,6); // 6
        app.touch(2,6); // 5
        app.touch(2,5); // 2
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(1,7); // 7
        app.touch(2,6); // 5
        app.touch(3,5); // 3
        // Card Number digits
        app.touch(1,2); // focus on card number to enter rest of card no
        app.touch(1,6); // 4
        app.touch(1,1); // out of focus
        app.touch(1,6); // 4
        app.touch(1,2); // focus back on card number to enter rest of card no
        app.touch(1,8); // space must be no action
        app.touch(1,7); // 7
        app.touch(1,6); // 4
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[159652474]", lines[4].trim());
        assertEquals("[753]", lines[5].trim());
        app.touch(1,8); // space must be no action
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[159652474]", lines[4].trim());
        assertEquals("[753]", lines[5].trim());
    }


    @Test
    public void AddCardTest8()
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
        app.next() ;
        app.display() ;
        assertEquals("AddCard", app.screen());
        lines = app.screenContents().split("\n");
        assertEquals("[]", lines[4].trim());
        assertEquals("[]", lines[5].trim());
        // Card Code digits
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.next() ;
        app.display() ;
        assertEquals("AddCard", app.screen());
        lines = app.screenContents().split("\n");
        assertEquals("[]", lines[4].trim());
        assertEquals("[]", lines[5].trim());
        app.touch(1,2); // focus on card number
        app.touch(1,5); // 1
        app.touch(2,5); // 2
        app.touch(3,5); // 3
        app.touch(1,6); // 4
        app.touch(2,6); // 5
        app.touch(3,6); // 6
        app.next() ;
        app.display() ;
        assertEquals("AddCard", app.screen());
        lines = app.screenContents().split("\n");
        assertEquals("[]", lines[4].trim());
        assertEquals("[]", lines[5].trim());
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
    public void SettingsTest2() {
        assertEquals("PinScreen", app.screen());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen());
        app.touch(1,1) ; // Add New Card
        assertEquals("AddCard", app.screen());
        app.prev() ;
        assertEquals("Settings", app.screen());
        app.touch(2,1) ; // Add New Card
        assertEquals("AddCard", app.screen());
        app.next() ; // Try !! Should not move to next screen without card inputs
        assertEquals("AddCard", app.screen());
        app.prev() ;
        assertEquals("Settings", app.screen());
    }


    @Test
    public void SettingsTest4() {
        String[] lines ;
        assertEquals("PinScreen", app.screen());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.execute("E") ; // Settings Page
        assertEquals("Settings", app.screen());
        app.touch(4,1) ; // Wrong touch co-ordinate
        assertEquals("Settings", app.screen());
    }

    @Test
    public void MenuTestTest3()
    {
        String[] lines ;
        String line ;
        String[] word;
        assertEquals("PinScreen", app.screen());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("MyCards", app.screen());
        lines = app.screenContents().split("\n");
        assertEquals("$0.00", lines[7].trim());
        app.display() ;
        app.execute("D") ;
        assertEquals("Store", app.screen());
        lines = app.screenContents().split("\n");
        line = lines[7].trim();
        word  = ((String) line).split(" ");
        assertEquals ("x", word[0]);
        app.display() ;
    }



    @Test
    public void MenuTestTest4()
    {
        String[] lines ;
        String line ;
        String[] word;
        assertEquals("PinScreen", app.screen());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("MyCards", app.screen());
        lines = app.screenContents().split("\n");
        assertEquals("$0.00", lines[7].trim());
        app.display() ;
        app.execute("E") ;
        assertEquals("Settings", app.screen());
        app.touch(1,1) ; // Add New Card
        assertEquals("AddCard", app.screen());
        app.execute("A") ; //Jump back from deep inside screen to Menu
        assertEquals("MyCards", app.screen());
    }

      @Test
    public void PaymentsTest2()
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
        app.touch(1,5);
        app.touch(2,5);
        app.touch(3,5);
        app.touch(1,6);
        app.touch(2,6);
        app.touch(3,6);
        app.touch(1,7);
        app.touch(2,7);
        app.touch(3,7);
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(3,7);
        app.touch(3,7);
        app.touch(3,7);
        // check digit entry
        lines = app.screenContents().split("\n");
        assertEquals("[123456789]", lines[4].trim());
        assertEquals("[999]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("MyCards", app.screen());
        lines = app.screenContents().split("\n");
        //assertEquals("$20.00", lines[7].trim());
        assertTrue("Failed", lines[7].contains("$20.00"));
        // switch to payment
        app.touch(3,3);
        lines = app.screenContents().split("\n");
        assertEquals("[123456789]", lines[6].trim());
        assertEquals("Scan Now", lines[9].trim());
        // Make Payments
        app.touch(2,2);  // Pay $1.50
        app.touch(3,3); // switch to balance
        lines = app.screenContents().split("\n");
        //assertEquals("$18.50", lines[7].trim());
        assertTrue("Failed", lines[7].contains("$18.50"));
        app.touch(3,3); //Switch to payment
        app.touch(2,2);  // Pay $1.50
        app.touch(3,3); // switch to balance
        lines = app.screenContents().split("\n");
        assertTrue("Failed", lines[7].contains("$17.00"));
        app.touch(3,3); //Switch to payment
        app.touch(2,2);  // Pay $1.50
        app.touch(3,3); // switch to balance
        lines = app.screenContents().split("\n");
        assertTrue("Failed", lines[7].contains("$15.50"));
        app.touch(3,3); //Switch to payment
        app.touch(2,2);  // Pay $1.50
        app.touch(3,3); // switch to balance
        lines = app.screenContents().split("\n");
        assertTrue("Failed", lines[7].contains("$14.00"));
        app.touch(3,3); //Switch to payment
        app.touch(2,2);  // Pay $1.50
        app.touch(3,3); // switch to balance
        lines = app.screenContents().split("\n");
        assertTrue("Failed", lines[7].contains("$12.50"));
        app.touch(3,3); //Switch to payment
        app.touch(2,2);  // Pay $1.50
        app.touch(3,3); // switch to balance
        lines = app.screenContents().split("\n");
        assertTrue("Failed", lines[7].contains("$11.00"));
        app.touch(3,3); //Switch to payment
        app.touch(2,2);  // Pay $1.50
        app.touch(3,3); // switch to balance
        lines = app.screenContents().split("\n");
        assertTrue("Failed", lines[7].contains("$9.50"));
        app.touch(3,3); //Switch to payment
        app.touch(2,2);  // Pay $1.50
        app.touch(3,3); // switch to balance
        lines = app.screenContents().split("\n");
        assertTrue("Failed", lines[7].contains("$8.00"));
        app.touch(3,3); //Switch to payment
        app.touch(2,2);  // Pay $1.50
        app.touch(3,3); // switch to balance
        lines = app.screenContents().split("\n");
        assertTrue("Failed", lines[7].contains("$6.50"));
        app.touch(3,3); //Switch to payment
        app.touch(2,2);  // Pay $1.50
        app.touch(3,3); // switch to balance
        lines = app.screenContents().split("\n");
        assertTrue("Failed", lines[7].contains("$5.00"));
        app.touch(3,3); //Switch to payment
        app.touch(2,2);  // Pay $1.50
        app.touch(3,3); // switch to balance
        lines = app.screenContents().split("\n");
        assertTrue("Failed", lines[7].contains("$3.50"));
        app.touch(3,3); //Switch to payment
        app.touch(2,2);  // Pay $1.50
        app.touch(3,3); // switch to balance
        lines = app.screenContents().split("\n");
        assertTrue("Failed", lines[7].contains("$2.00"));
        app.touch(3,3); //Switch to payment
        app.touch(2,2);  // Pay $1.50
        app.touch(3,3); // switch to balance
        lines = app.screenContents().split("\n");
        assertTrue("Failed", lines[7].contains("$0.50"));
        app.touch(3,3); //Switch to payment
        app.touch(2,2);  // Pay $1.50
        app.touch(3,3); // switch to balance
        lines = app.screenContents().split("\n");
        assertTrue("Failed", lines[7].contains("$0.50"));
    }

    @Test
    public void StrategyPatternTest6()
    {
        String[] lines ;
        assertEquals("PinScreen", app.screen());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("MyCards", app.screen());
        app.portrait();
        assertEquals("MyCards", app.screen());
        app.execute( "B" ) ;
        assertEquals("Payments", app.screen());
        app.execute( "A" ) ;
        assertEquals("MyCards", app.screen());
        lines = app.screenContents().split("\n");
        assertEquals("$0.00", lines[7].trim());
        app.landscape();
        app.display() ;
        app.touch(2,4) ;
        assertEquals("MyCardsOptions", app.screen());
        lines = app.screenContents().split("\n");
        assertEquals("Reload",          lines[3].trim());
        assertEquals("Refresh",         lines[4].trim());
        assertEquals("More Options",    lines[5].trim());
        assertEquals("Cancel",          lines[6].trim());
        app.touch(1, 7) ;
        assertEquals("MyCardsMoreOptions", app.screen());
        app.execute( "E" ) ;
        assertEquals("MyCardsMoreOptions", app.screen());
        app.portrait(); //Move back to portrait again
        app.execute( "A" ) ;
        assertEquals("MyCards", app.screen());
        lines = app.screenContents().split("\n");
        assertEquals("$0.00", lines[7].trim());
    }

     @Test
    public void InvalidPin()
    {
        String[] lines ;
        assertEquals("PinScreen", app.screen());
        app.touch(1,5) ;  // 1
        app.touch(1,5) ;  // 1
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        lines = app.screenContents().split("\n");
        assertEquals("Invalid Pin", lines[3].trim());
        app.touch(1,5) ;  // 1
        app.touch(2,5) ;  // 2
        app.touch(3,5) ;  // 3
        app.touch(1,6) ;  // 4
        assertEquals("MyCards", app.screen());
    }

    @After
    public void tearDown()
    {
    }
}
