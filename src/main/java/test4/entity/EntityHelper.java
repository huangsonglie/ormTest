package test4.entity;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public class EntityHelper {
    public <T> T createEntity(Class<T> t, ResultSet rs) throws Exception {
        if (rs == null) return null;
        // 创建新的实体对象
        Object instance = t.newInstance();
        Field[] fields = t.getFields();
        for (Field field : fields) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            String columnName = columnAnnotation.value();
            Object value = rs.getObject(columnName);
            field.set(instance, value);
        }
        return (T) instance;
    }
}
