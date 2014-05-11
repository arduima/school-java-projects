package edu.jkwsql.dataobjects;

/**
 * Created by Dimitri on 4/18/14.
 * Data Object to store database settings
 */
public class DatabaseSettingsDO {
    private String databaseName;
    private String tableName;
    private String host;
    private int portNumber;
    private String username;
    private String password;

    public DatabaseSettingsDO() {
    }

    public DatabaseSettingsDO(String databaseName, String tableName, String host, int portNumber, String username, String password) {
        this.databaseName = databaseName;
        this.tableName = tableName;
        this.host = host;
        this.portNumber = portNumber;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "DatabaseSettings [" +
                "databaseName=" + databaseName + ", " +
                "tableName=" + tableName + ", " +
                "host=" + host + ", " +
                "portNumber=" + portNumber + ", " +
                "username=" + username + ", " +
                "password=" + password +
                "]";
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getHost() {
        return host;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
