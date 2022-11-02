package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageRepository {

    private final static  String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final static String USERNAME = "postgres";
    private final static String PASSWORD = "tmazleo";

    public void insertMessage(final Message message) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "insert into messages values(?, ?)";
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, message.getId());
            preparedStatement.setString(2, message.getMessage());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    public List<Message> findAll() {
        String sql = "select * from messages";
        PreparedStatement preparedStatement = null;
        try(Connection connection = ConnectionFactory.getConnection()) {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Message> messages = new ArrayList<>();
            while (resultSet.next()) {
                Message message = new Message(resultSet.getLong(1), resultSet.getString(2));
                messages.add(message);
            }
            resultSet.close();
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void update(Message message) {
        String sql = "update messages set message = ? where id = ?";
        PreparedStatement preparedStatement = null;
        try(Connection connection = ConnectionFactory.getConnection()) {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, message.getMessage());
            preparedStatement.setLong(2, message.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        String sql = "delete from messages where id = ?";
        PreparedStatement preparedStatement = null;
        try (Connection connection = ConnectionFactory.getConnection()){
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
