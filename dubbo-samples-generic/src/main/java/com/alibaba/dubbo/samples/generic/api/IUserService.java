package com.alibaba.dubbo.samples.generic.api;

import java.io.Serializable;

import com.alibaba.dubbo.samples.generic.api.IUserService.Params;
import com.alibaba.dubbo.samples.generic.api.IUserService.User;

/**
 * @author zmx ON 2018/4/19
 */
public interface IUserService extends IService<Params, User> {


    public static class Params implements Serializable {
        private static final long serialVersionUID = 1L;

        public Params(String query) {
        }
    }

    public static class User implements Serializable {
        private static final long serialVersionUID = 1L;
        private int id;
        private String name;
        public User(int id, String name) {
            super();
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User [id=" + id + ", name=" + name + "]";
        }
    }
}
