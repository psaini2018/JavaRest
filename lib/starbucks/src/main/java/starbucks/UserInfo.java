package starbucks;

public class UserInfo implements IUserInfo {

    private static UserInfo userInfo;
    private static String DEFAULT_PIN = "1234";

    String  username;
    String  pin;
    String  phone;
    String  email;

    private UserInfo(){

        this.username = "";
        this.phone = "";
        this.email = "";
        this.pin = DEFAULT_PIN;
    }

    /**
     * Static Constructor
     * @return this object.
     */
    public static UserInfo getInstance() {
        if (userInfo == null) {
            userInfo = new UserInfo();
        }
        return userInfo;
    }

    @Override
    public void SetUserName(String username) {

        this.username = username;
    }

    @Override
    public String GetUserName() {
        return this.username;
    }

    @Override
    public void SetPin(String username, String pin) {
        if(this.username.equals(username)){
            this.pin = pin;
        }
    }

    @Override
    public String GetPin(String username) {
        if(this.username.equals(username)){
            return this.pin;
        }
        return DEFAULT_PIN;
    }

    @Override
    public void SetPhoneNumber(String username, String phone) {
        if(this.username.equals(username)){
            this.phone = phone;
        }
    }

    @Override
    public void SetEmailId(String username, String email) {
        if(this.username.equals(username)){
            this.email = email;
        }
    }
}
