package io.swagger.model;

import io.swagger.model.Entity.AccountEntity;
import org.junit.Assert;
import org.junit.Test;

public class AccountEntityModelTests {

    @Test
    public void newAccountShouldNeverBeNull(){
        AccountEntity accountEntity = new AccountEntity();
        Assert.assertNotNull(accountEntity);
    }
}
