package collectionmanager.databasetools;

import network.CurrentUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserChecker {

    private final Connection connection;

    public UserChecker(Connection connection) {
        this.connection = connection;
    }

    private static String GET_USER_ROW = "SELECT login, password FROM musicband_users WHERE login = ?";
    public static String SIGN_UP_USER = "INSERT INTO musicband_users VALUES (?, ?)";

    private boolean checkPassword(CurrentUser currentUser) throws DatabaseException {
        boolean isApproved = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ROW);
            preparedStatement.setString(1, currentUser.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isApproved = (
                        resultSet.getString("login").equals(currentUser.getLogin()) &&
                                Arrays.equals(resultSet.getBytes("password"), currentUser.getPassword())
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Can't check password");
        }
        return isApproved;
    }

    public boolean checkUser(CurrentUser currentUser) throws DatabaseException {
        boolean isApproved = false;
        if (checkLogin(currentUser)) {
            isApproved = checkPassword(currentUser);
        }
        return isApproved;
    }

    public boolean checkLogin(CurrentUser currentUser) throws DatabaseException {
        boolean isRegistered = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ROW);
            preparedStatement.setString(1, currentUser.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isRegistered = resultSet.getString("login").equals(currentUser.getLogin());
            }
        } catch (SQLException e) {
            throw new DatabaseException("Can't check login");
        }
        return isRegistered;
    }

    public boolean signUpUser(CurrentUser currentUser) throws DatabaseException {
        boolean isSignedUp = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SIGN_UP_USER);
            preparedStatement.setString(1, currentUser.getLogin());
            preparedStatement.setBytes(2, currentUser.getPassword());
            isSignedUp = preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DatabaseException("Can't sign up a new user");
        }
        return isSignedUp;
    }


}
