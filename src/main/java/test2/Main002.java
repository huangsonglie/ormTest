package test2;

import test2.entity.EntityHelper;
import test2.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Main002 {
    public static void main(String[] args) throws Exception{
        // 加载 Mysql 驱动
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        // 数据库连接地址
        String dbConnStr = "jdbc:mysql://localhost:3306/ormtest?user=root&password=123456";
        // 创建数据库连接
        Connection conn = DriverManager.getConnection(dbConnStr);
        // 简历陈述对象
        Statement stmt = conn.createStatement();

        // 创建 SQL 查询
        // ormtest 数据库中有个 t_user 数据表,
        // t_user 数据表包括三个字段: user_id、user_name、password,
        // t_user 数据表有 20 万条数据
        String sql = "select * from user limit 200000";

        // 执行查询
        ResultSet rs = stmt.executeQuery(sql);

        // 获取开始时间
        long t0 = System.currentTimeMillis();

        while (rs.next()) {
            User user = EntityHelper.createEntity(rs);
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
