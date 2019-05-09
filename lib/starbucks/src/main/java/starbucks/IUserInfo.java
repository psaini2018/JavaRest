package starbucks;

public interface IUserInfo {

    void SetUserName(String username);

    String GetUserName();

    void SetPin(String username, String pin);

    String GetPin(String username);

    void SetPhoneNumber(String username, String phone);

    void SetEmailId(String username, String phone);
}
