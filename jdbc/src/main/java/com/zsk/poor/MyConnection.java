package com.zsk.poor;


import com.zsk.uilt.AbstConnection;

import java.sql.*;

/**
 * 连接类
 *
 * 采用静态代理模式，便于统一管理和修改
 *
 *
 */
public class MyConnection extends AbstConnection {
    private Connection conn;
    private boolean tem = false;//false表示空闲，true表示正在使用
    private static String forName = (String) ReadProperties.getValues("forName");
    private String url = (String) ReadProperties.getValues("url");
    private String user = (String) ReadProperties.getValues("user");
    private String apassword = (String) ReadProperties.getValues("apassword");

    static {
        try {//加载驱动，并执行一次
            Class.forName(forName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    {//给conn赋值
        try {
            conn = (Connection) DriverManager.getConnection(url, user, apassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //获取连接
    public Connection getConn() {
        return conn;
    }

    public boolean isTem() {
        return tem;
    }

    public void setTem(boolean tem) {
        this.tem = tem;
    }
    //释放连接
    public void close() throws SQLException {
        this.setTem(false);
    }
    public Statement createStatement() throws SQLException {
        return this.conn.createStatement();
    }
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return this.conn.prepareStatement(sql);
    }
}


