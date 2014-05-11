package edu.jkwsql.database;

import edu.jkwsql.dataobjects.DatabaseSettingsDO;

import java.sql.*;

/**
 * Created by dimitrikoshkin on 4/19/14.
 */
public class DatabaseProcessor {
    DatabaseSettingsDO databaseSettingsDO;
    Connection connection;

    public DatabaseProcessor(DatabaseSettingsDO databaseSettingsDO) {
        this.databaseSettingsDO = databaseSettingsDO;
    }

    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");     //Loads the required driver
            System.out.println("Success loading Driver!");
        } catch (Exception exception) {
            System.out.println("Fail loading Driver!");
            exception.printStackTrace();
        }

        Connection connection = null;
        try {
            String url = "jdbc:postgresql://" + databaseSettingsDO.getHost() + ":" + databaseSettingsDO.getPortNumber() + "/" + databaseSettingsDO.getDatabaseName();
            connection = DriverManager.getConnection(url, databaseSettingsDO.getUsername(), databaseSettingsDO.getPassword());    //connect to the database using the password and username
        } catch (SQLException e) {
            System.out.println("Connection URL or username or password errors!");
            e.printStackTrace();
        }
        this.connection = connection;
    }

    public ResultSet executeQuery(String query) {
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = statement.executeQuery(query);              //executing the query
        } catch (SQLException e) {
//            TODO handle exception
            e.printStackTrace();
        }
        return result;
    }

    public String getDataType(ResultSet resultSet, String field) {
        String dataType = "";
        try {
            while (resultSet.next()) {
                if (resultSet.getString("column_name").equals(field))
                    dataType = resultSet.getString("data_type");
            }
            resultSet.first();
        } catch (SQLException e) {
//            TODO handle exception
            e.printStackTrace();
        }
        return dataType;
    }
}