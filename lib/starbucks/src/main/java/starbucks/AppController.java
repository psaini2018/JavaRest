

package starbucks;

/**
 * Main App Controller Class
 */
public class AppController implements IApp {

    private IScreen mycards;
    private IScreen store;
    private IScreen rewards;
    private IScreen payments;
    private IScreen settings;
    private IMenuCommand displayMyCards;
    private IMenuCommand displayPayments;
    private IMenuCommand displayRewards;
    private IMenuCommand doStore;
    private IMenuCommand doSetting;
    private IFrame frame;

    public AppController() {
        frame = Frame.getInstance();
        mycards = new MyCards();
        store = new Store();
        rewards = new Rewards();
        payments = new Payments();
        settings = new Settings();
        frame.setCurrentScreen(mycards);
        frame.portrait(); //Default

        // setup command pattern
        displayMyCards = new MenuCommand();
        displayPayments = new MenuCommand();
        displayRewards = new MenuCommand();
        doStore = new MenuCommand();
        doSetting = new MenuCommand();
        displayMyCards.setReceiver(
                new IMenuReceiver() {
                    /** Command Action */
                    public void doAction() {
                        frame.setCurrentScreen(mycards);
                    }
                }
        );
        displayPayments.setReceiver(
                new IMenuReceiver() {
                    /** Command Action */
                    public void doAction() {
                        frame.setCurrentScreen(payments);
                    }
                }
        );
        displayRewards.setReceiver(
                new IMenuReceiver() {
                    /** Command Action */
                    public void doAction() {
                        frame.setCurrentScreen(rewards);
                    }
                }
        );
        doStore.setReceiver(
                new IMenuReceiver() {
                    /** Command Action */
                    public void doAction() {
                        frame.setCurrentScreen(store);
                    }
                }
        );
        doSetting.setReceiver(
                new IMenuReceiver() {
                    /** Command Action */
                    public void doAction() {
                        frame.setCurrentScreen(settings);
                    }
                }
        );
        frame.setMenuItem("A", displayMyCards);
        frame.setMenuItem("B", displayPayments);
        frame.setMenuItem("C", displayRewards);
        frame.setMenuItem("D", doStore);
        frame.setMenuItem("E", doSetting);
    }


    /**
     * Switch to Landscape Mode
     */
    public void landscape() {
        frame.landscape();
    }

    /**
     * Switch to Portait Mode
     */
    public void portrait() {
        frame.portrait();
    }

    /**
     * Send In Touch Events
     *
     * @param x X Coord
     * @param y Y Coord
     */
    public void touch(int x, int y) {
        frame.touch(x, y);
    }

    /**
     * Display Current Screen
     */
    public void display() {
        frame.display();
    }

    /**
     * Execute Menu Bar Command
     *
     * @param c Menu Bar Option (A, B, C, D or E)
     */
    public void execute(String c) {
        frame.cmd(c);
    }

    /**
     * Navigate to Previous Screen
     */
    public void prev() {
        frame.previousScreen();
    }

    /**
     * Navigate to Next Screen
     */
    public void next() {
        frame.nextScreen();
    }

    /**
     * Get Current Screen Name
     *
     * @return Screen Name
     */
    public String screen() {
        return frame.screen();
    }

    /**
     * Get Current Screen Contents
     *
     * @return Current Screen Contents
     */
    public String screenContents() {
        return frame.contents();
    }

}
