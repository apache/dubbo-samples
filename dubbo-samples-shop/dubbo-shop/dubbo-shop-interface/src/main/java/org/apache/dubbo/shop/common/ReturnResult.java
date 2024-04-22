package org.apache.dubbo.shop.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReturnResult <T>{


        private Integer code;//业务状态码  0-成功  1-失败
        private String message;//提示信息
        private T data;//响应数据

        //快速返回操作成功响应结果(带响应数据)
        public static <E> ReturnResult<E> success(E data) {
            return new ReturnResult<>(0, "操作成功", data);
        }

        //快速返回操作成功响应结果
        public static ReturnResult success() {
            return new ReturnResult(0, "操作成功", null);
        }

        public static ReturnResult error(String message) {
            return new ReturnResult(1, message, null);
        }

}
