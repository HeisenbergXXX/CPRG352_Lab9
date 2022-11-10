package services;

import dataaccess.UserDB;
import java.util.List;
import models.User;

public class UserService {

    public List<User> getAll() throws Exception {
        UserDB db = new UserDB();
        List<User> users = db.getAll();
        return users;
    }

    public User get(String email) throws Exception {
        UserDB udb = new UserDB();
        User user = udb.get(email);
        return user;
    }

    public void insert(User user) throws Exception {
        UserDB udb = new UserDB();
        udb.insert(user);
    }

    public void update(User user) throws Exception {
        UserDB udb = new UserDB();
        udb.update(user);
    }

    public void delete(String email) throws Exception {
        UserDB udb = new UserDB();
        User user = udb.get(email);
        udb.delete(user);
    }

}