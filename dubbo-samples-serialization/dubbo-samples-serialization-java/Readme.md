# Warning

Java serialization is unsafe. Dubbo Team do not recommend anyone to use it.
If you still want to use it, please follow [JEP 290](https://openjdk.java.net/jeps/290) 
to set serialization filter to prevent deserialization leak.