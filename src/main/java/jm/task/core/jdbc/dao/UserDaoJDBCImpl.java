package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.persistence.Column;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    public void tryingDao(String sqlCommand, String Message) {
        try (Connection conn = Util.getMySQLConnection()){
            Statement statement = conn.createStatement();
            statement.executeUpdate(sqlCommand);
            System.out.println(Message);
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createUsersTable() {
        tryingDao("CREATE TABLE if not exists User(" +
                "    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT ," +
                "    firstName CHAR(20)," +
                "    lastName CHAR(20)," +
                "    age TINYINT" +
                ")","Table is created");
    }

    public void dropUsersTable() {
        tryingDao("DROP TABLE User","Table is dropped");
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = Util.getMySQLConnection()){
            PreparedStatement preparedStatement = conn.
                    prepareStatement("insert into User(firstName,lastName,age)" +
                                                    " values(?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User с именем – "+name+" добавлен в базу данных");
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = Util.getMySQLConnection()){
            PreparedStatement preparedStatement = conn.
                    prepareStatement("DELETE FROM User WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User is removed");
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String sqlCommand = "select * from User";
        List<User> listUsers = new ArrayList<>();
        try (Connection conn = Util.getMySQLConnection()){
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while(resultSet.next()){
                Long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                Byte age = resultSet.getByte(4);
                User user = new User(name,lastName,age);
                user.setId(id);
                listUsers.add(user);
            }
         } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Users are recieved");
        return listUsers;
    }

    public void cleanUsersTable() {
        tryingDao("DELETE FROM User WHERE id >=1","Table is clean");
    }
}
