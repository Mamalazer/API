package in.reqres.pojo_classes;

import org.junit.jupiter.api.Test;

public class UpdatedUser {

    private String name;
    private String job;
    private String updatedAt;

    public UpdatedUser(String name, String job, String updatedAt) {
        this.name = name;
        this.job = job;
        this.updatedAt = updatedAt;
    }

    public UpdatedUser() {

    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

}
