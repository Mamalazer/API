package in.reqres.pojo_classes;

import java.util.Objects;

public class UserData {

    private Integer id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;

    public UserData(Integer id, String email, String first_name, String last_name, String avatar) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
    }

    public UserData() {

    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return Objects.equals(id, userData.id) && Objects.equals(email, userData.email) && Objects.equals(first_name, userData.first_name) && Objects.equals(last_name, userData.last_name) && Objects.equals(avatar, userData.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, first_name, last_name, avatar);
    }
}
