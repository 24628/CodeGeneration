package models;

import io.swagger.model.Entity.UserEntity;
import org.junit.Assert;
import org.junit.Test;

public class UserEntityModelTest {

    @Test
    public void newUserShouldNeverBeNull(){
        UserEntity userEntity = new UserEntity();
        Assert.assertNotNull(userEntity);
    }
}
