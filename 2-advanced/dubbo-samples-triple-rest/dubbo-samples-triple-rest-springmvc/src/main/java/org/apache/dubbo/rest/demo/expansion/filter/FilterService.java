package org.apache.dubbo.rest.demo.expansion.filter;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/filter")
public interface FilterService {
    @GetMapping(value = "/get/{name}", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
    public String filterGet(@PathVariable(value = "name") String name);
}
