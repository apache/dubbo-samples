package org.apache.dubbo.rest.demo.model;

import org.apache.dubbo.rest.demo.model.User;
import java.util.Objects;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import java.time.*;
import java.math.*;
@Generated(value = "org.openapitools.codegen.languages.JavaDubboServerCodegen", comments = "Generator version: 7.16.0-SNAPSHOT")

public class HelloRequest implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("user")
  private User user;

  /**
   * Count parameter
   */
  @JsonProperty("count")
  private Integer count;

  /**
   * 
   * @return user
   */
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Count parameter
   * @return count
   */
  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HelloRequest helloRequest = (HelloRequest) o;
    return Objects.equals(this.user, helloRequest.user) &&
        Objects.equals(this.count, helloRequest.count);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, count);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HelloRequest {\n");
    
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
