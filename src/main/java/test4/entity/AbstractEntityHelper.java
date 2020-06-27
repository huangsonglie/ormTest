package test4.entity;

import java.sql.ResultSet;

public abstract class AbstractEntityHelper {
    public abstract Object createEntity(ResultSet rs) throws Exception;
}
