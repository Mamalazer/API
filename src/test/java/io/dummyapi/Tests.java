package io.dummyapi;

import io.dummyapi.pojo_classes.User;
import org.junit.jupiter.api.Test;

import static io.dummyapi.user_data_steps.Steps.createUser;
import static io.dummyapi.user_data_steps.Steps.deleteUser;

public class Tests {

    @Test
    public void createAndDeleteUser() {
        User user = createUser();
        deleteUser(user);
    }

}
