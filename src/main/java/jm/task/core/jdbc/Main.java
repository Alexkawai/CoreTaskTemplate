package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("sdfgv","sdfsfd", (byte) 200);
        service.saveUser("fdvfvv","plplsfd", (byte) 100);
        List<User> list = service.getAllUsers();
        for(User u : list) {
            System.out.println(u.toString());
        }
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
