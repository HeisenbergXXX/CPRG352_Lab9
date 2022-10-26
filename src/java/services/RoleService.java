package services;

import dataaccess.RoleDB;
import java.util.List;
import models.Role;

public class RoleService {

    public List<Role> getAll() throws Exception {
        RoleDB db = new RoleDB();
        List<Role> roles = db.getAll();
        return roles;
    }
}