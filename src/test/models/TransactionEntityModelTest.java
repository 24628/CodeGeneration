package models;

import io.swagger.model.Entity.TransactionEntity;
import org.junit.Assert;
import org.junit.Test;

public class TransactionEntityModelTest {

    @Test
    public void newTransactionShouldNeverBeNull(){
        TransactionEntity transactionEntity = new TransactionEntity();
        Assert.assertNotNull(transactionEntity);
    }
}
