package com.zsk;



import com.zsk.poor.ConnectionPoor;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1导包
        //2加载驱动
//        Class.forName("com.mysql.jdbc.Driver");
//        String url="jdbc:mysql://localhost:3306/atm";
//        String user="root";
//        String password="root";
//        //3获取连接
//        Connection conn = DriverManager.getConnection(url, user, password);
//        //4创建状态参数
//        PreparedStatement pstat = conn.prepareStatement("update atm set balance=? where id=?");
//        //填充参数
//        pstat.setFloat(1,200);
//        pstat.setInt(2,1);
//        //5执行数据库操作
//        int i = pstat.executeUpdate();
//        System.out.println(i);
//        //6关闭连接
//        conn.close();

        Connection conn = ConnectionPoor.getPoor().getConnection();
        //4创建状态参数
        PreparedStatement pstat = conn.prepareStatement("select * from atm where id=?");
        //填充参数
        pstat.setFloat(1,1);

        //5执行数据库操作
         pstat.executeQuery();
        conn.close();


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Connection conn = ConnectionPoor.getPoor().getConnection();
//
//                System.out.println(conn);
//                try {
//                    Thread.sleep(1000);
//                    System.out.println("释放锁了");
//                    conn.close();
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Connection conn = ConnectionPoor.getPoor().getConnection();
//                System.out.println(conn);
//            }
//        }).start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Connection conn = ConnectionPoor.getPoor().getConnection();
//                System.out.println(conn);
//
//            }
//        }).start();






    }
}
