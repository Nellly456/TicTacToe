/* 
    Implements access to the MySQL database "tictactoe"
 */
package com.tttws;

import static java.lang.System.exit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author petar
 */
public class TTT_MySQL {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    /**
     * Parameterized constructor to connect to any MySQL DB on any MySQL DBMS.
     *
     * @param host where is the DBMS?
     * @param username who are you?
     * @param password what is your password?
     * @param database which database do you wish to use?
     */
    public TTT_MySQL(String host, String username, String password, String database) {
        String url = "";
        url = "jdbc:mysql://" + host + "/" + database;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            exit(-1);
        }
    }

    /**
     * Used to retrieve information from a table.
     *
     * @param SQLCmd The SELECT query you wish to run.
     * @return The result of the query encoded as a comma separated list of
     * fields with a new line for each row.
     */
    public String retrieve(String SQLCmd) {
        String result = "";
        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery(SQLCmd);
            result = writeResultSet(resultSet);
        } catch (Exception e) {
            return "ERROR";
        }
        return result;
    }

    /**
     * Used to update information in a table.
     *
     * @param SQLCmd The UPDATE command you wish to run.
     * @return either how many rows were updated or the text string "ERROR".
     */
    public String update(String SQLCmd) {
        try {
            preparedStatement = connect.prepareStatement(SQLCmd);
            int rows = preparedStatement.executeUpdate();
            return "UPDATED " + rows + " ROWS.";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    /**
     * Used to insert information into a table.
     *
     * @param SQLCmd The INSERT query you wish to run.
     * @return either how many rows were inserted or the text string "ERROR".
     */
    public String insert(String SQLCmd) {
        try {
            preparedStatement = connect.prepareStatement(SQLCmd);
            int rows = preparedStatement.executeUpdate();
            return "INSERTED " + rows + " ROWS.";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    /**
     * Used to remove data from a table.
     *
     * @param SQLCmd The DELETE query you wish to run.
     * @return either how many rows were removed or the text string "ERROR".
     */
    public String remove(String SQLCmd) {
        try {
            preparedStatement = connect.prepareStatement(SQLCmd);
            int rows = preparedStatement.executeUpdate();
            return "REMOVED " + rows + " ROWS.";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    /**
     * Used to get the fields names of a resultset.
     *
     * @param resultSet the resultset you wish to get the field names of.
     * @return the metadata of the resultset.
     * @throws SQLException
     */
    private String writeMetaData(ResultSet resultSet) throws SQLException {
        String result = "";
        result += "The columns in the table are: ";
        result += "Table: " + resultSet.getMetaData().getTableName(1);
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            result += "Column " + i + " " + resultSet.getMetaData().getColumnName(i);
        }
        return result;
    }

    /**
     * Used to turn a resultset into a digestible form.
     *
     * @param resultSet the resultset you wish to consume.
     * @return the resultset encoded as fields separated by commas and rows
     * separated by the new line character (\n).
     * @throws SQLException
     */
    private String writeResultSet(ResultSet resultSet) throws SQLException {
        String result = "";
        /*switch(query) {
            case "login":
                while(resultSet.next()) {
                    int userID = resultSet.getInt("autokey");
                    result += userID + "\n";
                }
            break;
                
        } */
        int cols = resultSet.getMetaData().getColumnCount();
        int rows = 0;
        while (resultSet.next()) {
            if (rows > 0) {
                result += "\n";
            }
            for (int i = 1; i <= cols; i++) {
                result += resultSet.getObject(i).toString();
                result += ",";
            }
            int len = result.length();
            result = result.substring(0, (len - 1));
            rows++;
        }
        return result;
    }

    /**
     * Used to close all objects used to talk with the DB and DBMS.
     */
    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
