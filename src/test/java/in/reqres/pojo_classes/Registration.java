package in.reqres.pojo_classes;

public class Registration {

    private String email;
    private String password;

    public Registration(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Registration() {

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
