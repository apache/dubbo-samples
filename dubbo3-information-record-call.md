# dubbo3 information record call
 了解 dubbo3 访问日志的配置和使用

## 特性说明:
 在 dubbo3日志分为日志适配和访问日志，如果想记录每一次请求信息，可开启访问日志，类似于 apache 的访问日志。
 
## 访问日志输出类型：
  - log4j日志
  - 指定文件
 
## 使用场景:
## 使用方式:
将访问日志输出到当前应用的log4j日志：

```xml
<dubbo:protocol accesslog="true" />
```
将访问日志输出到指定文件：

```xml
<dubbo:protocol accesslog="http://10.20.160.198/wiki/display/dubbo/foo/bar.log" />
```
#### 注意:
此日志量比较大，请注意磁盘容量。 
