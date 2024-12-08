/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.rest.swagger;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a user with details for demonstration purposes.
 */
@Schema(description = "Represents a user with name, age, and hobbies.")
public class User {

    @Schema(description = "Unique identifier of the user", example = "123")
    private Long id;

    @Schema(description = "Name of the user", example = "Liang He")
    private String name;

    @Schema(description = "Age of the user", example = "19")
    private Integer age;

    @Schema(description = "List of user's hobbies", example = "[\"reading\", \"traveling\"]")
    private List<String> hobbies;

    public User() {}

    public User(Long id, String name, Integer age, List<String> hobbies) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobbies = hobbies;
    }

    // Getters and Setters

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

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }
}
