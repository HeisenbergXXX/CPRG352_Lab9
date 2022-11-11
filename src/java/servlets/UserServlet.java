/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Role;
import models.User;
import services.RoleService;
import services.UserService;

/**
 * @author Bennett
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserService us = new UserService();
        RoleService rs = new RoleService();
        try {
            List<User> users = us.getAll();
            List<Role> roles = rs.getAll();
            request.setAttribute("users", users);
            request.setAttribute("roles", roles);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("messageGet", ex.getMessage());
        }

        String action = request.getParameter("action");
        if (action != null && action.equals("addUser")) {
            try {
                request.setAttribute("action", "addUser");
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", ex.getMessage());
            }
        }

        if (action != null && action.equals("edit")) {
            try {
                request.setAttribute("action", "edit");
                String email = request.getParameter("email");
                User user = us.get(email);

                request.setAttribute("email", user.getEmail());
                request.setAttribute("status", user.isActive());
                request.setAttribute("first_name", user.getFirst_name());
                request.setAttribute("last_name", user.getLast_name());
                request.setAttribute("password", user.getPassword());
                request.setAttribute("role", user.getRole());
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", ex.getMessage());
            }
        }


        request.getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserService us = new UserService();
        RoleService rs = new RoleService();
//        User user = new User();
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        boolean active = Boolean.parseBoolean(request.getParameter("status"));
        String firstname = request.getParameter("first_name");
        String lastname = request.getParameter("last_name");
        String password = request.getParameter("password");
        String roleStr = request.getParameter("role");

        //add, delete edit user
        try {
            switch (action) {
                case "add":

                    //input validation
                    if (email == null || email.isEmpty() ||
                            firstname == null || firstname.isEmpty() ||
                            lastname == null || lastname.isEmpty() ||
                            password == null || password.isEmpty())
                    {
                        request.setAttribute("message2", "Please fill out all fields");
                        try {
                            List<User> users = us.getAll();
                            List<Role> roles = rs.getAll();
                            request.setAttribute("users", users);
                            request.setAttribute("roles", roles);
                        } catch (Exception ex) {
                            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                            request.setAttribute("message", ex.getMessage());
                        }
                        request.setAttribute("first_name", firstname);
                        request.setAttribute("last_name", lastname);
                        request.setAttribute("email", email);
                        request.setAttribute("password", password);
                        request.setAttribute("status", active);
                        request.setAttribute("action", "addUser");
                        request.getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                        return;
                    }
                    Role role = new Role(Integer.parseInt(roleStr));
                    User user = new User(email, active, firstname, lastname, password, role);
                    us.insert(user);
                    request.setAttribute("messageADE", "User: "+email+" added successfully!");
                    break;
                case "update":

                    //input validation
                    if (    firstname == null || firstname.isEmpty() ||
                            lastname == null || lastname.isEmpty() ||
                            password == null || password.isEmpty())
                    {
                        request.setAttribute("message2", "Please fill out all fields");
                        try {
                            List<User> users = us.getAll();
                            List<Role> roles = rs.getAll();
                            request.setAttribute("users", users);
                            request.setAttribute("roles", roles);
                        } catch (Exception ex) {
                            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                            request.setAttribute("message", ex.getMessage());
                        }
                        request.setAttribute("first_name", firstname);
                        request.setAttribute("last_name", lastname);
                        request.setAttribute("email", email);
                        request.setAttribute("password", password);
                        request.setAttribute("status", active);
                        request.setAttribute("action", "update");
                        request.getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                        return;
                    }
                    role = new Role(Integer.parseInt(roleStr));
                    user = new User(email, active, firstname, lastname, password, role);
                    us.update(user);
                    request.setAttribute("messageADE", "User: "+email+" updated successfully!");
                    break;
                case "delete":
                    us.delete(email);
                    request.setAttribute("message", "Account "+email+" deleted!");
                    break;
                case "cancel":
                    request.setAttribute("action", null);
                    request.setAttribute("messageADE", "Action cancelled!");
                    break;
            }
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message2", ex.getMessage());
            try {
                List<User> users = us.getAll();
                List<Role> roles = rs.getAll();
                request.setAttribute("users", users);
                request.setAttribute("roles", roles);
            } catch (Exception ex2) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex2);
                request.setAttribute("message", ex2.getMessage());
            }
            request.setAttribute("first_name", firstname);
            request.setAttribute("last_name", lastname);
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("status", active);
            request.setAttribute("action", "addUser");
            request.getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
            return;
        }

        try {
            List<User> users = us.getAll();
            List<Role> roles = rs.getAll();
            request.setAttribute("users", users);
            request.setAttribute("roles", roles);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "m"+ex.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);


    }

}

