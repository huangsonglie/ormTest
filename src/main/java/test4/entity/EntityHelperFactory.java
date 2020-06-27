package test4.entity;

import javassist.*;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public final class EntityHelperFactory {

    private EntityHelperFactory() {}

    public static AbstractEntityHelper getHelper(Class<?> entityClazz) throws Exception {
        if (entityClazz == null) return null;

        ClassPool pool = ClassPool.getDefault();
        pool.appendSystemPath();

        pool.importPackage(ResultSet.class.getName());
        pool.importPackage(User.class.getName());

        CtClass abstractEntityHelperClazz = pool.getCtClass(AbstractEntityHelper.class.getName());
        String helperImplClazzName = entityClazz.getName() + "Helper";

        CtClass helperCtClazz = pool.makeClass(helperImplClazzName, abstractEntityHelperClazz);
        CtConstructor constructor = new CtConstructor(new CtClass[0], helperCtClazz);
        constructor.setBody("{}");
        helperCtClazz.addConstructor(constructor);

        StringBuilder methodSb = new StringBuilder();
        methodSb.append("public Object createEntity(java.sql.ResultSet rs) throws Exception{\n");
        //        User user = new User();
        methodSb.append(entityClazz.getName() + " user = new " + entityClazz.getName() + "();\n");
        Field[] fields = User.class.getFields();

//        user.userId = rs.getLong("user_id");
//        user.userName = rs.getString("user_name");
//        user.password = rs.getString("user_password");
        for (Field field : fields) {
            Class<?> type = field.getType();
            String fieldName = field.getName();
            Column annotation = field.getAnnotation(Column.class);
            if (annotation == null) continue;
            String columnName = annotation.value();

            if (type == Long.TYPE) {
                methodSb.append("user.")
                        .append(fieldName)
                        .append(" = rs.getLong(\"")
                        .append(columnName)
                        .append("\");\n");
            } else if (type.equals(String.class)) {
                methodSb.append("user.")
                        .append(fieldName)
                        .append(" = rs.getString(\"")
                        .append(columnName)
                        .append("\");\n");
            }
        }
        methodSb.append("return user;\n");

        methodSb.append("}");
        CtMethod method = CtNewMethod.make(methodSb.toString(), helperCtClazz);
        helperCtClazz.addMethod(method);
        Class<?> helperClazz = helperCtClazz.toClass();
        AbstractEntityHelper helper = (AbstractEntityHelper) helperClazz.newInstance();
        return helper;
    }
}