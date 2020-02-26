### 1.Run naocs as config center

### 2.Add config (dataId=dubbo.properties group=dubbo)
	dubbo.service.com.ikurento.user.UserProvider.cluster=failback
	dubbo.service.com.ikurento.user.UserProvider.protocol=myDubbo
	dubbo.protocols.myDubbo.port=20000
	dubbo.protocols.myDubbo.name=dubbo

### 3.Run go server

### 4.Run go client 
