# Dubbo Transaction Example

This example shows how a global transaction manager works in Dubbo framework. Here we use [seata](https://github.com/seata/seata) as an example, but other transaction managers are possible to work with Dubbo too if they provide the corresponding Dubbo filter implementation.

## How To Run

#### Step 1. Start Registry Center, Transaction Manager and Database

In this example, a docker compose file is provided to start registry center, transaction manager and database all together very easily. We use zookeeper as Dubbo's registry center, and [seata](https://github.com/seata/seata) as transaction manager and mysql as database server. But at the same time, it requires you to prepare docker environment as a prerequisite. You can refer to [docker quick start](https://www.docker.com/get-started) to install.

```bash
cd src/main/resources/docker
docker-compose up
```

#### Step 2. Build Examples

Execute the following maven command under *dubbo-samples-transaction* directory, or simply import the whole sample project into IDE.

```bash
mvn clean package
```

#### Step 3. Run Examples

In this examples, there are three Dubbo services provided, which are *AccountService*, *OrderService*, *StorageService*:

0. Execute *DubboAccountServiceStarter* to start *AccountService*
0. Execute *DubboOrderServiceStarter* to start *OrderService*
0. Execute *DubboStorageServiceStarter* to start *StorageService*

*BusinessService* is not a Dubbo service but a normal Spring bean. It's used to verify the distributed transaction among *AccountService*, *OrderService*, *StorageService*. If transaction succeeds, then *storageService* will deduct commodity from the storage, *orderService* will place the order and eventually debit from user's account by *AccountService*. 

```java
@GlobalTransactional(timeoutMills = 300000, name = "dubbo-demo-tx")
public void purchase(String userId, String commodityCode, int orderCount) {
    LOGGER.info("purchase begin ... xid: " + RootContext.getXID());
    storageService.deduct(commodityCode, orderCount);
    orderService.create(userId, commodityCode, orderCount);
    throw new RuntimeException("xxx");
}
```

In this example, since *BusinessService*'s *purchase* method throw an Exception within the transaction, this transaction will not succeed. You can verify the balance from the database tables *storage_tbl*, *order_tbl*, *account_tbl*.

To run *BusinessService*, simply launch *DubboBusinessTester*.

This example shows how a distributed transaction is used in Dubbo application with classic spring. For more advanced usage, pls. refer to [santa samples project](https://github.com/seata/seata-samples)
