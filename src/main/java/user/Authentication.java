package user;

import userinterface.UI;

public class Authentication {
    String username;
    UI ui;
    private String password;
    

    public Authentication(String password, String username, UI ui) {
        this.password = password;
        this.username = username;
        this.ui = ui;
    }

    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }

    public Boolean checkPassword(String username, String password) {
        return this.password.equals(password) && this.username.equals(username);
    }

    public Boolean changePassword(String username, String oldPassword, String newPassword) {
        if (!checkPassword(username, oldPassword)) {
            return false;
        }
        this.password = newPassword;
        return true;
    }

    public Boolean authenticate(String inputUsername) throws Exception {
        System.out.println("Password: ");
        String inputPassword = this.ui.readInput();
        boolean result = this.checkPassword(inputUsername, inputPassword);
        if (!result) {
            throw new Exception("Incorrect password");
        }
        return result;
    }


}
