package org.apache.dubbo.rest.demo.pojo;
import java.io.Serializable;
import java.util.Objects;

// 需要 implements Serializable
public class User implements Serializable{

    private Long id;

    private String name;

    private Integer age;

    public User(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public User(Long id, String name, Integer age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static User getInstance() {
        User user = new User();
        user.setAge(18);
        user.setName("dubbo");
        user.setId(404l);
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", age=" + age + '}';
    }

    public String stringToJson(){
        return "{\"id\":\"" + this.id + "\", \"name\":\"" + this.name + "\"}";
    }

}
