package in.reqres.pojo_classes;

public class SuccessfulRegistration {

    private Integer id;
    private String token;

    public SuccessfulRegistration(int id, String token) {
        this.id = id;
        this.token = token;
    }

    public SuccessfulRegistration() {

    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

}
