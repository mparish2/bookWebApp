/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Matthew_2
 */
public class MySqlDBStrategy implements DBStrategy {

    private Connection conn;

    //what would be better this or bring it in from the construct. think about/research
    /**
     *
     * @param driverClassName
     * @param url
     * @param username
     * @param password
     * @throws IllegalArgumentException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public void openConnection(String driverClassName, String url, String username, String password)
            throws IllegalArgumentException, ClassNotFoundException, SQLException {
        Class.forName(driverClassName);
        conn = DriverManager.getConnection(url, username, password);
    }

    /**
     *
     * @throws SQLException
     */
    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }

    /**
     * Make sure you open and close connection when using this method. Future
     * optimization may include change the return type an array.
     *
     * @param maxRecords - limits records fount to first maxRecords or if
     * maxRecords is zero (0) then no limit.
     * @param tableName
     * @return
     */
    @Override
    public List<Map<String, Object>> findAllRecords(String tableName, int maxRecords) throws SQLException {

        String sql;
        if (maxRecords < 1) {
            sql = "select * from " + tableName;
        } else {
            sql = "select * from " + tableName + " limits " + maxRecords;
        }

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ResultSetMetaData rsmd = rs.getMetaData();
        /*provides information about the table. */
        int columnCount = rsmd.getColumnCount();
        List<Map<String, Object>> records = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> record = new HashMap<>();
            for (int colNo = 1; colNo <= columnCount; colNo++) {  //(orig had < "" + 1) <= or == ask Mr.Lombardo
                Object colData = rs.getObject(colNo);
                String colName = rsmd.getColumnClassName(colNo);
                record.put(colName, colData);
            }
            records.add(record);
        }
        return records;
    }

    /**
     * Make sure you open and close connection when using this method.
     *
     * @param tableName
     * @param columnName
     * @param keyValue
     * @return
     * @throws SQLException
     */
    @Override
    public int deleteRecordbyID(String tableName, String columnName, Object keyValue) throws SQLException {

        int recordsDeleted = 0;
        PreparedStatement pstmt = null;

        String sql2 = null;

        if (keyValue instanceof String) {
            sql2 = "= '" + keyValue + "'";
        } else {
            sql2 = "=" + keyValue;
        }
        final String sql = "Delete FROM " + tableName + " WHERE " + columnName + sql2;
        pstmt = conn.prepareStatement(sql);

        recordsDeleted = pstmt.executeUpdate(sql);

        return recordsDeleted;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBStrategy db = new MySqlDBStrategy();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");

        List<Map<String, Object>> rawData = db.findAllRecords("author", 0);

        //int del = db.deleteRecordbyID("author","author_id",3);
        db.closeConnection();

        System.out.println(rawData);

    }
}
