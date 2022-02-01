package in.reqres.pojo_classes;

public class CreatedUser {

    private String name;
    private String job;
    private String id;
    private String createdAt;

    public CreatedUser(String name, String job, String id, String createdAt) {
        this.name = name;
        this.job = job;
        this.id = id;
        this.createdAt = createdAt;
    }

    public CreatedUser() {
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

}
