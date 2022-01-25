package in.reqres.pojo_classes;

public class SingleUser {

    UserData data;
    Support support;

    public SingleUser(UserData data, Support support) {
        this.data = data;
        this.support = support;
    }

    public SingleUser() {

    }

    public UserData getData() {
        return data;
    }

    public Support getSupport() {
        return support;
    }
}
