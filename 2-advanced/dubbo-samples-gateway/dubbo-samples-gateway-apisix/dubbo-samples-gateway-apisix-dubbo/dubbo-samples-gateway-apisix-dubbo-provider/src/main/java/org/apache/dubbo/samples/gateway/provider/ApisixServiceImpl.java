package org.apache.dubbo.samples.gateway.provider;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.samples.api.ApisixService;

import java.util.HashMap;
import java.util.Map;

@DubboService
public class ApisixServiceImpl implements ApisixService {

    @Override
    public Map<String, Object> apisixToDubbo(Map<String, Object> requestBody) {
        for (Map.Entry<String, Object> entry : requestBody.entrySet()) {
            //注意这里body的value实际是byte数组
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("body", "dubbo success\n"); // http response body
        resp.put("status", "200"); // http response status
        resp.put("author", "yang siming"); // http response header

        return resp;
    }
}
