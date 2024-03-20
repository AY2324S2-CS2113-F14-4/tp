package user;

import userinterface.UI;
import storage.Storage;

public class BaseUser {
    String name;
    Authentication auth;
    UI ui;

    public BaseUser(String name, UI ui) throws Exception{
        this.name = name;
        String username = name.replace(" ", "_");
        Storage storage = new Storage("./data");
        String pw = storage.getPassword(username);
        if (pw == null) {
            throw new Exception("User does not exist");
        }
        this.auth = new Authentication(storage.getPassword(username), username, ui);
        this.ui = ui;
    }

    public String getName() {
        return name;
    }

    public Authentication getAuthentication(){
        return this.auth;
    }
}
