package test3;

import test3.entity.EntityHelper;
import test3.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main003 {
    public static void main(String[] args) throws Exception{
        // 加载 Mysql 驱动
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        // 数据库连接地址
        String dbConnStr = "jdbc:mysql://localhost:3306/ormtest?user=root&password=123456";
        // 创建数据库连接
        Connection conn = DriverManager.getConnection(dbConnStr);
        // 简历陈述对象
        Statement stmt = conn.createStatement();

        String sql = "select * from user limit 200000";

        // 执行查询
        ResultSet rs = stmt.executeQuery(sql);

        // 获取开始时间
        long t0 = System.currentTimeMillis();

        while (rs.next()) {
            User user = new EntityHelper().createEntity(User.class, rs);
        }

        // 获取结束时间
        long t1 = System.currentTimeMillis();

        // 关闭数据库连接
        stmt.close();
        conn.close();

        // 打印实例化花费时间
        System.out.println("实例化花费时间 = " + (t1 - t0) + "ms");

    }
}
