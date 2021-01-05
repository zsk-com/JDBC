package com.zsk.pool;


import com.zsk.uilt.Runtime;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 连接池
 */
public class ConnectionPool {

    //乐观锁实现并发
    private ReentrantLock lock=new ReentrantLock();
    //获取好多连接，并存集合里
    private static ArrayList<Connection> conn=new ArrayList();
    /**
     *     设置单例模式
     */
    //1.私有的构造方法
    private ConnectionPool(){
    }
    //2.存在一个当前类私有的静态的对象
    private volatile static ConnectionPool poor;//volatile是为了防止指令重排序
    //3.提供一个获取唯一对象的方法
    //懒汉式单例模式
    public static ConnectionPool getPoor(){//双重if判断
        if (poor==null){
            synchronized(ConnectionPool.class){
                if (poor==null){
                    poor=new ConnectionPool();
                }
            }
        }
        return poor;
    }

    // nuberConn 连接最大值
    private static int nuberConn=Integer.parseInt((String) ReadProperties.getValues("nuberConn"));
    static {
        for (int i=0;i<nuberConn;i++){
            conn.add(new MyConnection());
        }
    }

    /**
     * 加锁，防止并发多个线程拿到同一个对象
     * @return 连接对象
     */
    private  Connection getConn() {
       Connection rulic=null;
        for (int i=0;i<conn.size();i++){
            MyConnection rs=(MyConnection)conn.get(i);
            //尝试获取锁，获取不到则不等待
            try {
                //锁定当前线程池的对象
                lock.lock();
                if (rs.isTem() == false) {//找到一个链接
                    rs.setTem(true);//将链接占为即有
                    rulic = rs;
                    break;
                }
            }finally {
                //释放锁
               lock.unlock();
            }
        }
        return rulic;
    }

    /**
     * 添加排队等待机制
     * @return 连接对象
     */
    public Connection getConnection(){
        Connection conn = this.getConn();
        int size=0;
        while(conn==null && size<50){
            conn=this.getConn();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            size++;
        }
            if (conn==null){
                throw new Runtime("请刷新重新");
            }
        return conn;
    }
}
