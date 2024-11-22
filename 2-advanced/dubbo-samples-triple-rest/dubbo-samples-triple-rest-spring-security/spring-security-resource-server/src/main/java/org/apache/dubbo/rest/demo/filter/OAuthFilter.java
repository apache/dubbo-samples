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

package org.apache.dubbo.rest.demo.filter;


import jakarta.servlet.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import org.apache.dubbo.rpc.protocol.tri.rest.filter.RestExtension;

import java.io.IOException;

public class OAuthFilter implements Filter, RestExtension {

    @Override
    public String[] getPatterns() {
        return new String[] {"/**"}; // Intercept all requests
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws ServletException, IOException {
        System.out.println("OAuthFilter.doFilter");
        System.out.println("servletRequest" + servletRequest);
        System.out.println("servletResponse" + servletResponse);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public int getPriority() {
        return -200;
    }
}
