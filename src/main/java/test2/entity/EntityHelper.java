package test2.entity;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public class EntityHelper {
    public static User createEntity(ResultSet rs) throws Exception {
        // 创建新的实体对象
        User user = new User();
        Field[] fields = User.class.getFields();
        for (Field field : fields) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            String columnName = columnAnnotation.value();
            Object value = rs.getObject(columnName);
            field.set(user, value);
        }
        return user;
    }
}
