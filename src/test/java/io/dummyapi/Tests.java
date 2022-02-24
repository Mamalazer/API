package io.dummyapi;

import io.dummyapi.pojo_classes.User;
import org.junit.jupiter.api.Test;

import static io.dummyapi.user_data_steps.Steps.*;

public class Tests {

    @Test
    public void createAndDeleteUser() {
        User user = createUser();
        deleteUser(user);
    }

    @Test
    public void getAllUsers() {
        getUsers(0, 20);
    }

    @Test
    public void updateUserInfo() {
        User user = createUser();
        updateUser(user);
        deleteUser(user);
        getDeletedUserById(user);
    }

}
