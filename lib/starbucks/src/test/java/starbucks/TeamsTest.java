package starbucks;

        import static org.junit.Assert.*;
        import org.junit.After;
        import org.junit.Before;
        import org.junit.Test;

public class TeamsTest
{

    IApp app ;
    PinEntryMachine pinEntry ;

    public TeamsTest()
    {
    }

    @Before
    public void setUp()
    {
        app = new AppAuthProxy() ;
        pinEntry = new PinEntryMachine();
    }

    @Test
    public void MoreAddCardTest1()
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
        app.next();
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[]", lines[4].trim());
        // add card - see balance
    }


    @Test
    public void MoreAddCardTest2()
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
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(1,7); // 7
        app.touch(2,6); // 5
        app.touch(3,5); // 3
        // check digit entry
        app.next();
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[]", lines[4].trim());
        assertEquals("[]", lines[5].trim());
    }

    @Test
    public void MoreAddCardTest3()
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
        // check digit entry
        app.prev();
        app.display() ;
        assertEquals("Settings", app.screen());
    }

    @Test
    public void MorePinMachineTest1()
    {
        pinEntry.number("1");
        pinEntry.number("23456789");
        assertEquals("starbucks.TwoPinDigits", pinEntry.getCurrentState());
    }

    @Test
    public void MorePinMachineTest2()
    {
        pinEntry.number("1");
        pinEntry.number("234");//Read only digit[0] and ignore rest of string;
        assertEquals("starbucks.TwoPinDigits", pinEntry.getCurrentState());
        pinEntry.number("3");
        pinEntry.number("4");
        assertEquals("starbucks.NoPinDigits", pinEntry.getCurrentState());
    }

    @Test
    public void MorePasscodeTest1()
    {
        String[] lines ;
        assertEquals("PinScreen", app.screen());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(2,6) ;
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("Invalid Pin", lines[3].trim());

    }

    @Test
    public void MorePasscodeTest2()
    {
        String[] lines ;
        assertEquals("PinScreen", app.screen());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(2,6) ;
        app.touch(1,5) ;
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[*][_][_][_]", lines[5].trim());
    }

    @Test
    public void MorePasscodeTest3()
    {
        String[] lines ;
        assertEquals("PinScreen", app.screen());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(2,6) ;
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[*][*][*][_]", lines[5].trim());
    }

    @Test
    public void MorePasscodeTest4()
    {
        String[] lines ;
        assertEquals("PinScreen", app.screen());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(2,6) ;
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        app.display() ;
        assertEquals("MyCards", app.screen());
    }

    @Test
    public void MorePasscodeTest5()
    {
        String[] lines ;
        assertEquals("PinScreen", app.screen());
        app.touch(1,5) ;
        app.execute("login") ;
        assertEquals("PinScreen", app.screen());
    }


    @Test
    public void NewTestCasesTest1()
    {
        String[] lines;
        assertEquals("PinScreen", app.screen());
        app.touch(1,5) ;
        app.touch(1,5) ;
        app.touch(1,5) ;
        app.touch(1,5) ;
        lines = app.screenContents().split("\n");
        assertEquals("Invalid Pin", lines[3].trim());
    }

    @Test
    public void NewTestCasesTest2()
    {
        String[] lines;
        assertEquals("PinScreen", app.screen());
        app.touch(1,5) ;
        app.touch(1,5) ;
        app.touch(1,5) ;
        app.touch(1,5) ;
        lines = app.screenContents().split("\n");
        assertEquals("Invalid Pin", lines[3].trim());
        app.touch(1,5) ;
        app.touch(2,5) ;
        app.touch(3,5) ;
        app.touch(1,6) ;
        assertEquals("MyCards", app.screen());
    }


    @Test
    public void NewTestCasesTest3()
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
        app.prev();
        assertEquals("Settings", app.screen());

    }


    @Test
    public void NewTestCasesTest4()
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
        // check digit entry
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[123456789]", lines[4].trim());
        assertEquals("[99]", lines[5].trim());
        // add card - see balance
        app.next() ;
        assertEquals("AddCard", app.screen());
        lines = app.screenContents().split("\n");
        assertEquals("[]", lines[4].trim());
        assertEquals("[]", lines[5].trim());

    }

    @Test
    public void NewTestCasesTest5()
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
        app.touch(2,2) ; // Setting the Focus explicitely to CardId
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
        app.display() ;
        lines = app.screenContents().split("\n");
        assertNotEquals("[112233445]", lines[5].trim());

    }

    @Test
    public void NewTestCasesTest6()
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
        app.touch(2,3); // focus on card code
        // Card Code digits
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.touch(3,7); // 9
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[123456]", lines[4].trim());
        assertEquals("[999]", lines[5].trim());
    }


    @Test
    public void NewTestCasesTest7()
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
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[123456789]", lines[4].trim());
        app.touch(3,8);
        app.touch(3,8);
        app.touch(3,8);
        app.touch(3,8);
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[12345]", lines[4].trim());
    }


    @Test
    public void NewTestCasesTest8()
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
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[123456789]", lines[4].trim());
        assertEquals("[999]", lines[5].trim());
        app.touch(3,8);
        app.touch(3,8);
        app.touch(3,8);
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[123456789]", lines[4].trim());
        assertEquals("[]", lines[5].trim());
    }

    @Test
    public void NewTestCasesTest9()
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
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[123456789]", lines[4].trim());
        assertEquals("[999]", lines[5].trim());
        app.prev();
        assertEquals("Settings", app.screen());

    }

    @Test
    public void NewTestCasesTest10()
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
        app.display() ;
        lines = app.screenContents().split("\n");
        assertEquals("[123456789]", lines[4].trim());
        assertEquals("[999]", lines[5].trim());
        app.prev();
        assertEquals("Settings", app.screen());
        app.touch(1,1);
        assertEquals("AddCard", app.screen());
        app.display();
        lines = app.screenContents().split("\n");
        assertEquals("[]", lines[4].trim());
        assertEquals("[]", lines[5].trim());
    }


    @After
    public void tearDown()
    {
    }
}

