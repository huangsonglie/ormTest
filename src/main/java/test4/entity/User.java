package test4.entity;

public class User {
    @Column("user_id")
    public long userId;

    @Column("user_name")
    public String userName;

    @Column("user_password")
    public String password;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
