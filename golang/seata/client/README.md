### 运行步骤

+ 确保下面的环境变量有设置
```
echo $CONF_CONSUMER_FILE_PATH
echo $APP_LOG_CONF_FILE
echo $SEATA_CONF_FILE
```

+ 运行调试

**调试之前，请先运行事务协调器 tc server, JAVA 版和 GO 版本均可，Go 版地址 `https://github.com/dk-lockdown/seata-golang`**