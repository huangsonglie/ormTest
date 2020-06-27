package test1.entity;

import java.sql.ResultSet;

public class EntityHelper {
    public User createEntity(ResultSet rs) throws Exception{
        // 创建新的实体对象
        User ue = new User();

        ue.userId = rs.getLong("user_id");
        ue.userName = rs.getString("user_name");
        ue.password = rs.getString("user_password");
        return ue;
    }
}
